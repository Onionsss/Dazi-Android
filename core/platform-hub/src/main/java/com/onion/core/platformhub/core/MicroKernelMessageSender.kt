package com.onion.core.platformhub.core

import com.onion.core.platformhub.core.exceptions.MessageMultiRecipientException
import com.onion.core.platformhub.core.exceptions.MessageReceiverNotFoundException
import com.onion.core.platformhub.core.exceptions.MicroKernelUninitializedException
import com.onion.core.platformhub.core.exceptions.UnauthorizedMessageException
import com.onion.core.platformhub.core.extensions.messageId
import com.onion.core.platformhub.core.extensions.toLog
import com.onion.core.platformhub.core.extensions.toPoliceMessage
import com.onion.core.platformhub.core.models.BaseMessage
import com.onion.core.platformhub.core.models.EventStreamOutput
import com.onion.core.platformhub.core.models.LogInfo
import com.onion.core.platformhub.core.models.Message
import com.onion.core.platformhub.core.models.MessageType
import com.onion.core.platformhub.policy.model.PolicyMessage
import kotlinx.coroutines.flow.SharedFlow
import org.json.JSONObject
import timber.log.Timber

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: MicroKernelMessageSender
 * Author: admin by 张琦
 * Date: 2024/6/29 14:50
 * Description:
 */
internal class MicroKernelMessageSender(private val logger: Logger) : MessageSender {
    private var isReady: Boolean = false

    private val eventStreamPublishers = mutableMapOf<String, Plugin>()
    private val commandReceivers = mutableMapOf<String, Plugin>()
    private val queryReceivers = mutableMapOf<String, Plugin>()
    private val navigationQueryReceivers = mutableMapOf<String, Plugin>()

    fun init(plugins: List<Plugin>) {
        plugins.forEach { plugin -> register(plugin) }
        isReady = true
    }

    override fun subscribeOnDemand(
        eventStream: BaseMessage.EventStream,
        subscriber: Plugin
    ): SharedFlow<EventStreamOutput> {
        throwIfUnInitialised(eventStream, subscriber)

        return try {
            val publisher = getPublisherPlugin(eventStream, subscriber)
            logger.log(
                LogInfo.EventStreamInfo(
                    publisherPlugin = publisher.name(),
                    subscriberPlugins = listOf(subscriber.name()),
                    messageName = eventStream.messageName,
                    payload = eventStream.payload
                )
            )
            publisher.publish(eventStream)
        } catch (ex: RuntimeException) {
            logger.log(
                LogInfo.ErrorInfo(
                    senderPlugin = eventStream.pluginName,
                    receiverPlugin = subscriber.name(),
                    errorMessage = ex.localizedMessage.orEmpty(),
                    messageType = MessageType.EventStream,
                    messageName = eventStream.messageName,
                    payload = eventStream.payload
                )
            )
            throw ex
        }
    }

    override fun send(command: BaseMessage.Command, plugin: Plugin) {
        Timber.tag(MicroKernel.TAG).d("Sending message ${command.toLog()}")
        throwIfUnInitialised(command, plugin)
        try {
            val receiver = getReceiver(command, plugin)
            logger.log(
                LogInfo.MessageInfo(
                    senderPlugin = plugin.name(),
                    receiverPlugin = receiver.name(),
                    messageType = MessageType.Command,
                    messageName = command.messageName,
                    payload = command.payload,
                    responsePayload = null
                )
            )
            receiver.onReceive(command)
        } catch (ex: RuntimeException) {
            logError(plugin, command, MessageType.Command, ex.localizedMessage.orEmpty())
            throw ex
        }
    }

    override fun send(query: BaseMessage.Query, plugin: Plugin): JSONObject {
        Timber.tag(MicroKernel.TAG).d("Sending message ${query.toLog()}")
        throwIfUnInitialised(query, plugin)
        return try {
            val receiver = getReceiver(query, plugin)
            val response = receiver.onReceive(query)
            logger.log(
                LogInfo.MessageInfo(
                    senderPlugin = plugin.name(),
                    receiverPlugin = receiver.name(),
                    messageType = MessageType.Query,
                    messageName = query.messageName,
                    payload = query.payload,
                    responsePayload = response
                )
            )
            response
        } catch (ex: RuntimeException) {
            logError(plugin, query, MessageType.Query, ex.localizedMessage.orEmpty())
            throw ex
        }
    }
    override fun getReceiverForNavigation(message: Message, sender: Plugin): Plugin {
        throwIfUnInitialised(message, sender)
        return try {
            val receiver = getReceiver(navigation = message, sender = sender)
            logger.log(
                LogInfo.MessageInfo(
                    senderPlugin = sender.name(),
                    receiverPlugin = receiver.name(),
                    messageType = MessageType.NavigationQuery,
                    messageName = message.messageName,
                    payload = message.payload,
                    responsePayload = null
                )
            )
            receiver
        } catch (ex: RuntimeException) {
            logError(sender, message, MessageType.NavigationQuery, ex.localizedMessage.orEmpty())
            throw ex
        }
    }

    override fun getReceiverForNavigationList(message: Message, sender: Plugin): ArrayList<Plugin> {
        throwIfUnInitialised(message, sender)
        return try {
            val receiver = getReceiver(navigation = message, sender = sender)
            logger.log(
                LogInfo.MessageInfo(
                    senderPlugin = sender.name(),
                    receiverPlugin = receiver.name(),
                    messageType = MessageType.NavigationQuery,
                    messageName = message.messageName,
                    payload = message.payload,
                    responsePayload = null
                )
            )
            arrayListOf(receiver)
        } catch (ex: RuntimeException) {
            logError(sender, message, MessageType.NavigationQuery, ex.localizedMessage.orEmpty())
            throw ex
        }
    }

    private fun throwIfUnInitialised(message: Message, sender: Plugin) {
        if (!isReady) {
            val errorMessage = "Attempting to send a message but microkernel is not initialised."
            val messageType = when (message) {
                is BaseMessage.Command -> MessageType.Command
                is BaseMessage.Query -> MessageType.Query
                is BaseMessage.EventStream -> MessageType.EventStream
                else -> MessageType.NavigationQuery
            }
            logError(
                senderPlugin = sender,
                message = message,
                messageType = messageType,
                errorMessage = errorMessage
            )
            throw MicroKernelUninitializedException(
                "Message \"$message\" was sent by \"${sender.name()}\", but the microkernel is not initialized yet."
            )
        }
    }
    private fun logError(
        senderPlugin: Plugin,
        message: Message,
        messageType: MessageType,
        errorMessage: String
    ) {
        logger.log(
            LogInfo.ErrorInfo(
                senderPlugin = senderPlugin.name(),
                receiverPlugin = message.pluginName,
                errorMessage = errorMessage,
                messageType = messageType,
                messageName = message.messageName,
                payload = message.payload
            )
        )
    }
    private fun register(plugin: Plugin) {
        registerPluginMessages(
            plugin = plugin,
            messages = plugin.policy.publish.eventStreams,
            messagePluginMap = eventStreamPublishers
        )
        registerPluginMessages(
            plugin = plugin,
            messages = plugin.policy.receive.commands,
            messagePluginMap = commandReceivers
        )
        registerPluginMessages(
            plugin = plugin,
            messages = plugin.policy.receive.navigationQueries,
            messagePluginMap = navigationQueryReceivers
        )
        registerPluginMessages(
            plugin = plugin,
            messages = plugin.policy.receive.queries,
            messagePluginMap = queryReceivers
        )
    }
    private fun registerPluginMessages(
        plugin: Plugin,
        messages: List<PolicyMessage>,
        messagePluginMap: MutableMap<String, Plugin>
    ) {
        for (message in messages) {
            val existingPlugin = messagePluginMap[message.messageId(plugin)]

            if (existingPlugin != null) {
                throw MessageMultiRecipientException(
                    getErrorMessages(
                        message = message,
                        plugin = plugin,
                        existingPlugin = existingPlugin
                    )
                )
            }

            messagePluginMap[message.messageId(plugin)] = plugin
            Timber.tag(TAG).d(getSuccessMessages(message = message, plugin = plugin))
        }
    }
    private fun getErrorMessages(
        message: PolicyMessage,
        plugin: Plugin,
        existingPlugin: Plugin
    ) = when (message) {
        is PolicyMessage.EventStreamPolicy -> "❌ ERROR registering \"(${plugin.name()})\" " +
                "as a publisher of event stream: \"(${message.messageId(plugin)})\". " +
                "Only 1 plugin can publish an event stream but \"(${existingPlugin.name()})\" " +
                "is already registered as the publisher. Please review the plugin policies."
        is PolicyMessage.CommandPolicy -> "❌ ERROR registering \"(${plugin.name()})\" " +
                "as receiver of command: \"(${message.messageId(plugin)})\". Only 1 plugin can receive a " +
                "command but \"(${existingPlugin.name()})\" is already registered as the receiver. " +
                "Please review the plugin policies."

        is PolicyMessage.NavigationQueryPolicy -> "❌ ERROR registering \"(${plugin.name()})\" " +
                "as receiver of navigation query: \"(${message.messageId(plugin)})\". Only 1 plugin can " +
                "receive a navigation query but \"(${existingPlugin.name()})\" is already registered " +
                "as the receiver. Please review the plugin policies."
        is PolicyMessage.QueryPolicy -> "❌ ERROR registering \"(${plugin.name()})\" " +
                "as receiver of a query: \"(${message.messageId(plugin)})\". Only 1 plugin can receive " +
                "a query but \"(${existingPlugin.name()})\" is already registered " +
                "as the receiver. Please review the plugin policies."
    }
    private fun getSuccessMessages(
        message: PolicyMessage,
        plugin: Plugin
    ) = when (message) {
        is PolicyMessage.EventStreamPolicy -> "✅ MessageSender: registered \"(${plugin.name()})\" " +
                "as publisher of event stream: \"(${message.messageId(plugin)})\""

        is PolicyMessage.CommandPolicy -> "✅ MessageSender: registered \"(${plugin.name()})\" " +
                "as receiver of command: \"(${message.messageId(plugin)})\""

        is PolicyMessage.NavigationQueryPolicy -> "✅ MessageSender: registered \"(${plugin.name()})\" " +
                "as receiver of navigation query: \"(${message.messageId(plugin)})\""

        is PolicyMessage.QueryPolicy -> "✅ MessageSender: registered \"(${plugin.name()})\" " +
                "as receiver of query: \"(${message.messageId(plugin)})\""
    }

    private fun getReceiver(command: BaseMessage.Command, sender: Plugin): Plugin {
        val policyCommand = command.toPoliceMessage()

        if (sender.policy.send.commands.contains(policyCommand).not()) {
            throw UnauthorizedMessageException(
                "\"(${sender.name()})\" is not entitled to send the command \"(${policyCommand.messageId()})\". " +
                        "Please review \"(${sender.name()})\" policy to make sure you are sending the correct message type"
            )
        }

        return commandReceivers.getOrElse(policyCommand.messageId()) {
            val expectedReceiver = policyCommand.messageId().split(".").first()
            throw MessageReceiverNotFoundException(
                "The command \"(${policyCommand.messageId()})\" has no receiver. Please make sure " +
                        "\"($expectedReceiver)\" is added in the plugin list of the app or \"($expectedReceiver)\" " +
                        "has declared in its policy to receive \"(${policyCommand.messageId()})\"."
            )
        }
    }
    private fun getReceiver(query: BaseMessage.Query, sender: Plugin): Plugin {
        val policyQuery = query.toPoliceMessage()

        if (sender.policy.send.queries.contains(policyQuery).not()) {
            throw UnauthorizedMessageException(
                "\"(${sender.name()})\" is not entitled to send the query \"(${policyQuery.messageId()})\". " +
                        "Please review \"(${sender.name()})\" policy to make sure you are sending the correct message type"
            )
        }

        return queryReceivers.getOrElse(policyQuery.messageId()) {
            val expectedReceiver = policyQuery.messageId().split(".").first()
            throw MessageReceiverNotFoundException(
                "The query \"(${policyQuery.messageId()})\" has no receiver. Please make sure " +
                        "\"($expectedReceiver)\" is added in the plugin list of the app or \"($expectedReceiver)\" " +
                        "has declared in its policy to receive \"(${policyQuery.messageId()})\"."
            )
        }
    }
    private fun getReceiver(navigation: Message, sender: Plugin): Plugin {
        val policyNavigation = navigation.toPoliceMessage()

        if (sender.policy.send.navigationQueries.contains(policyNavigation).not()) {
            throw UnauthorizedMessageException(
                "\"(${sender.name()})\" is not entitled to send the navigation: " +
                        "\"(${policyNavigation.messageId()})\". Please review \"(${sender.name()})\" " +
                        "policy to make sure you are sending the correct message type"
            )
        }
        return navigationQueryReceivers.getOrElse(policyNavigation.messageId()) {
            val expectedReceiver = policyNavigation.messageId().split(".").first()
            throw MessageReceiverNotFoundException(
                "The navigation \"(${policyNavigation.messageId()})\" has no receiver. Please make sure " +
                        "\"($expectedReceiver)\" is added in the plugin list of the app or \"($expectedReceiver)\" " +
                        "has declared in its policy to receive \"(${policyNavigation.messageId()})\"."
            )
        }
    }
    private fun getPublisherPlugin(event: Message, subscriber: Plugin): Plugin {
        val eventStreams = subscriber.policy.subscribeOnDemand.eventStreams
        val eventStreamId = event.toPoliceMessage().messageId()

        if (eventStreams.contains(event.toPoliceMessage()).not()) {
            throw UnauthorizedMessageException(
                "The subscriber \"${subscriber.name()}\" is not entitled to subscribe to " +
                        "the event stream \"$eventStreamId\". Please make sure the event stream \"$eventStreamId\" " +
                        "is declared in the policy of \"${subscriber.name()}\" as subscribe on demand."
            )
        }
        return eventStreamPublishers.getOrElse(eventStreamId) {
            val expectedReceiver = eventStreamId.split(".").first()
            throw MessageReceiverNotFoundException(
                "The event stream \"($eventStreamId)\" has no publisher. Please make sure " +
                        "\"($expectedReceiver)\" is added in the plugin list of the app or \"($expectedReceiver)\" " +
                        "has declared in its policy to publish \"($eventStreamId)\"."
            )
        }
    }

// endregion

    private companion object {
        private const val TAG = "MessageDispatcher"
    }
}