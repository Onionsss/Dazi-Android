package com.onion.core.platformhub.core

import com.onion.core.platformhub.core.exceptions.DuplicateMessageException
import com.onion.core.platformhub.core.exceptions.DuplicatePluginException
import com.onion.core.platformhub.core.exceptions.InvalidMessageName
import com.onion.core.platformhub.core.exceptions.InvalidPluginName
import com.onion.core.platformhub.core.exceptions.MessageSubscribedAndPublishedException
import com.onion.core.platformhub.core.exceptions.UnusedPluginException
import com.onion.core.platformhub.core.extensions.allMessages
import com.onion.core.platformhub.core.extensions.messageId
import com.onion.core.platformhub.policy.Policy
import com.onion.core.platformhub.policy.model.PolicyMessage

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: PolicyValidator
 * Author: admin by 张琦
 * Date: 2024/6/29 14:54
 * Description:
 */
class PolicyValidator {

    private val directMessageReceivers = mutableMapOf<String, Plugin>()
    private val directMessageSenders = mutableMapOf<String, MutableList<Plugin>>()
    private val eventStreamSubscribers = mutableMapOf<String, MutableList<Plugin>>()
    private val eventStreamPublishers = mutableMapOf<String, Plugin>()
    
    fun validate(plugins: List<Plugin>) {
        plugins.forEach { plugin ->
            registerMessages(plugin)
            checkPluginNameFormat(plugin)
            checkMessageNameFormat(plugin)
            checkNoOverlapInPublishedAndSubscribedMessages(plugin)
            checkForDuplicateMessages(plugin)
            checkMessageOwnerForSendAndSubscribe(plugin.policy)
        }
        checkForDuplicatePluginNames(plugins)
        checkIfPluginIsUnUsed(plugins)
    }
    private fun registerMessages(plugin: Plugin) {
        for (eventStream in plugin.policy.publish.eventStreams) {
            val existingPublisher = eventStreamPublishers[eventStream.messageId(plugin)]

            if (existingPublisher == null) {
                eventStreamPublishers[eventStream.messageId(plugin)] = plugin
            }
        }

        for (message in plugin.policy.receive.allMessages()) {
            val existingReceiver = directMessageReceivers[message.messageId(plugin)]

            if (existingReceiver == null) {
                directMessageReceivers[message.messageId(plugin)] = plugin
            }
        }
        val eventStreams = plugin.policy.subscribeOnStartup.eventStreams +
                plugin.policy.subscribeOnDemand.eventStreams

        for (eventStream in eventStreams) {
            val existingSubscriber = eventStreamSubscribers.getOrPut(eventStream.messageId()) {
                mutableListOf()
            }
            eventStreamSubscribers[eventStream.messageId()] =
                existingSubscriber.apply { add(plugin) }
        }

        for (message in plugin.policy.send.allMessages()) {
            val existingSender = directMessageSenders.getOrPut(message.messageId()) {
                mutableListOf()
            }
            directMessageSenders[message.messageId()] = existingSender.apply { add(plugin) }
        }
    }
    private fun checkPluginNameFormat(plugin: Plugin) {
        if (nameRegex.matchEntire(plugin.name()) == null) {
            throw InvalidPluginName(
                "❌ Plugin name must be pascal case. \"${plugin.name()}\" does not conform to the plugin " +
                        "naming convention"
            )
        }

        if (plugin.name() != plugin.simpleName()) {
            throw InvalidPluginName(
                "❌ Plugin policy name \"(${plugin.name()})\" must be the same as classname " +
                        "\"(${plugin::class.simpleName})\""
            )
        }
    }

    private fun checkMessageNameFormat(plugin: Plugin) {
        val messages = getPublishedSubscribedMessages(plugin.policy).first +
                getPublishedSubscribedMessages(plugin.policy).second

        messages.forEach { message ->
            if (message.messageName.isBlank()) {
                throw InvalidMessageName(
                    "❌ \"${plugin.name()}\" has an empty message name in its policy file."
                )
            }

            if (nameRegex.matchEntire(message.messageName) == null) {
                throw InvalidMessageName(
                    "❌ Message name must be pascal case. \"${message.messageName}\" " +
                            "does not conform to the naming convention in \"${plugin.name()}\" policy file"
                )
            }
        }
    }
    private fun checkNoOverlapInPublishedAndSubscribedMessages(plugin: Plugin) {
        val outgoingMessages = getPublishedSubscribedMessages(plugin.policy).first
        val incomingMessages = getPublishedSubscribedMessages(plugin.policy).second

        val publishedAndSubscribedMessages = incomingMessages.intersect(outgoingMessages.toSet())

        if (publishedAndSubscribedMessages.isNotEmpty()) {
            throw MessageSubscribedAndPublishedException(
                "❌ \"${plugin.name()}\" cannot subscribe or receive the same messages it publish or send. " +
                        "This is happening for these messages: $publishedAndSubscribedMessages"
            )
        }
    }
    private fun checkIfPluginIsUnUsed(plugins: List<Plugin>) {
        plugins.forEach { plugin ->
            val hasDirectMessageReceiver = hasReceiverOrPublisherForMessage(
                messages = plugin.policy.send.allMessages(),
                messagePluginMap = directMessageReceivers
            )
            val hasDirectMessageSender = hasSendersOrSubscribersForMessage(
                messages = plugin.policy.receive.allMessages(),
                plugin = plugin,
                sendersSubscribersMap = directMessageSenders
            )
            val hasEventStreamPublisher = hasReceiverOrPublisherForMessage(
                messages = plugin.policy.subscribeOnStartup.eventStreams + plugin.policy.subscribeOnDemand.eventStreams,
                messagePluginMap = eventStreamPublishers
            )
            val hasEventStreamSubscriber = hasSendersOrSubscribersForMessage(
                messages = plugin.policy.publish.eventStreams,
                plugin = plugin,
                sendersSubscribersMap = eventStreamSubscribers
            )

            val isUnused = hasDirectMessageReceiver.not() && hasDirectMessageSender.not() &&
                    hasEventStreamSubscriber.not() && hasEventStreamPublisher.not()
            if (isUnused) {
                //todo hub
//                throw UnusedPluginException(
//                    "❌ \"${plugin.name()}\" doesn't seem to be used. All of its outgoing messages " +
//                            "doesn't have any receivers or subscribers and it doesn't receive or " +
//                            "subscribe to any incoming message from other plugins. Please review " +
//                            "\"${plugin.name()}\" policy to make sure the messages are properly configured"
//                )
            }
        }
    }
    private fun hasReceiverOrPublisherForMessage(
        messages: List<PolicyMessage>,
        messagePluginMap: MutableMap<String, Plugin>
    ): Boolean {
        messages.forEach { message ->
            if (messagePluginMap[message.messageId()] != null) return true
        }
        return false
    }
    private fun hasSendersOrSubscribersForMessage(
        messages: List<PolicyMessage>,
        plugin: Plugin,
        sendersSubscribersMap: MutableMap<String, MutableList<Plugin>>
    ): Boolean {
        messages.forEach { message ->
            if (!sendersSubscribersMap[message.messageId(plugin)].isNullOrEmpty()) return true
        }
        return false
    }
    private fun checkForDuplicateMessages(plugin: Plugin) {
        val publishEventStreams = plugin.policy.publish.eventStreams.map { it.messageId(plugin) }
        val sendDirectMessages = plugin.policy.send.allMessages().map { it.messageId() }
        val outgoingMessages = publishEventStreams + sendDirectMessages

        val subscribeStreams = plugin.policy.subscribeOnStartup.eventStreams +
                plugin.policy.subscribeOnDemand.eventStreams

        val subscribeEventStreams = subscribeStreams.map { it.messageId() }
        val receiveDirectMessages = plugin.policy.receive.allMessages().map { it.messageId(plugin) }
        val incomingMessages = subscribeEventStreams + receiveDirectMessages
        if (hasDuplicateMessages(outgoingMessages)) {
            throw DuplicateMessageException(
                "❌ \"${plugin.name()}\" has a duplicate message in its outgoing messages. " +
                        "Please review the policy file."
            )
        }

        if (hasDuplicateMessages(incomingMessages)) {
            throw DuplicateMessageException(
                "❌ \"${plugin.name()}\" has a duplicate message in its incoming messages. " +
                        "Please review the policy file"
            )
        }
    }
    private fun hasDuplicateMessages(messages: List<String>): Boolean =
        messages.size != messages.distinct().size

    private fun getPublishedSubscribedMessages(policy: Policy): Pair<List<PolicyMessage>, List<PolicyMessage>> =
        policy.run {
            Pair(
                publish.eventStreams + send.allMessages(),
                subscribeOnDemand.eventStreams + subscribeOnStartup.eventStreams + receive.allMessages()
            )
        }
    private fun checkForDuplicatePluginNames(plugins: List<Plugin>) {
        val duplicatePlugins = plugins.filter { plugin ->
            plugins.count { it.name().equals(other = plugin.name(), ignoreCase = true) } > 1
        }
        val duplicateNames = duplicatePlugins.map { it.name() }.toSet()

        if (duplicatePlugins.isNotEmpty()) {
            throw DuplicatePluginException(
                "❌ The following plugins: $duplicatePlugins has the same name \"$duplicateNames\" " +
                        "defined in their policy. Please review their policy files."
            )
        }
    }
    private fun checkMessageOwnerForSendAndSubscribe(policy: Policy) {
        val sentMessages = policy.send.allMessages()
        val subscribeMessages = policy.subscribeOnDemand.eventStreams +
                policy.subscribeOnStartup.eventStreams
        val messages = sentMessages + subscribeMessages

        messages.forEach { message ->
            if (message.pluginName.isNullOrBlank()) {
                throw InvalidMessageName(
                    "❌ Plugin name is missing for message: \"${message.messageName}\" " +
                            "in \"${policy.name}\". Send messages and subscribe event streams should " +
                            "define plugin name for the message owner."
                )
            }
        }
    }
    private companion object {
        private val nameRegex = "^[A-Z](([a-z0-9]+[A-Z]?)*)\$".toRegex()
    }

}