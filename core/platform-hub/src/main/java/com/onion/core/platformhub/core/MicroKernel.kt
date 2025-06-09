package com.onion.core.platformhub.core

import android.content.Context
import com.onion.core.platformhub.core.di.coroutines.DispatchersProviderImpl
import com.onion.core.platformhub.core.logger.LoggingDecorator
import com.onion.core.platformhub.core.models.BaseMessage
import com.onion.core.platformhub.core.models.LogInfo
import timber.log.Timber
import java.io.InputStream

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: MicroKernel
 * Author: admin by 张琦
 * Date: 2024/6/29 14:47
 * Description:
 */
internal class MicroKernel(
    private val logger: Logger,
    private val messageSender: MicroKernelMessageSender = MicroKernelMessageSender(logger),
    private val policyValidator: PolicyValidator = PolicyValidator()
) {
    private lateinit var pluginsWithConfigurations: List<Pair<Plugin, Map<String, InputStream>>>
    private val dispatchersProviderImpl = DispatchersProviderImpl()

    fun init(
        applicationContext: Context,
        pluginsWithConfigurations: List<Pair<Plugin, Map<String, InputStream>>>
    ) {
        Timber.tag(TAG).d("initializing the platform hub")
        this.pluginsWithConfigurations = pluginsWithConfigurations

        val allPluginNames: MutableList<String> = mutableListOf()
        val plugins = pluginsWithConfigurations.map { it.first }

        policyValidator.validate(plugins)
        val privacySchemas = logger.privacySchemas.toMutableList()
        pluginsWithConfigurations.forEach { pluginAndConfigMap ->
            val plugin = pluginAndConfigMap.first
            plugin.init(
                applicationContext,
                messageSender,
                pluginAndConfigMap.second
            )
            val policy = plugin.policy
            allPluginNames.add(policy.name)
            Timber.tag(TAG).d("Read policy ${policy.name}.")
            Timber.tag(TAG).d("Publishes messages: ${policy.publish}.")
            Timber.tag(TAG).d("Subscribes at startup to messages: ${policy.subscribeOnStartup}.")
            Timber.tag(TAG).d("Subscribes on demand to messages: ${policy.subscribeOnDemand}.")
            Timber.tag(TAG).d("sends messages: ${policy.send}.")
            Timber.tag(TAG).d("receives messages: ${policy.receive}.")

            privacySchemas.addAll(plugin.privacySchemas)
        }
        subscribePluginToEvents(plugins)
        messageSender.init(plugins)
        logger.mergeSchemas(privacySchemas)

        plugins.forEach { it.postMicroKernelInit() }
    }

    private fun subscribePluginToEvents(plugins: List<Plugin>) {
        for (plugin in plugins) {
            subscribeOnStartup(plugins = plugins, publisherPlugin = plugin)
        }
    }
    private fun subscribeOnStartup(plugins: List<Plugin>, publisherPlugin: Plugin) {
        for (eventStream in publisherPlugin.policy.publish.eventStreams) {
            val subscribers = mutableListOf<String>()
            plugins.filter { it.policy.subscribeOnStartup.eventStreams.contains(eventStream) }
                .forEach { subscriberPlugin ->
                    val forEvent = BaseMessage.EventStream(
                        messageName = eventStream.messageName,
                        pluginName = eventStream.pluginName.orEmpty()
                    )
                    val subscription = publisherPlugin.publish(forEvent)
                    val logInfo = LogInfo.EventStreamInfo(
                        publisherPlugin = publisherPlugin.name(),
                        subscriberPlugins = subscribers,
                        messageName = eventStream.messageName,
                        payload = null
                    )
                    val sharedFlow = LoggingDecorator(subscription, logger, logInfo, dispatchersProviderImpl).getSharedFlow()
                    subscriberPlugin.subscribeOnStartup(
                        subscription = sharedFlow,
                        eventStream = forEvent
                    )
                    subscribers.add(subscriberPlugin.name())
                }

            logger.log(
                LogInfo.EventStreamInfo(
                    publisherPlugin = publisherPlugin.name(),
                    subscriberPlugins = subscribers,
                    messageName = eventStream.messageName,
                    payload = null
                )
            )
        }
    }
    companion object {
        const val TAG = "platform-hub"
    }
}