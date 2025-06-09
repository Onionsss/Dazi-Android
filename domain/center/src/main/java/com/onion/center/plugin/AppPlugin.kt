package com.onion.center.plugin

import android.content.Context
import com.onion.core.platformhub.core.Plugin
import com.onion.core.platformhub.core.models.BaseMessage
import com.onion.core.platformhub.core.models.EventStreamOutput
import com.onion.core.platformhub.core.util.AssetsLoader
import com.onion.core.platformhub.handler.IPluginQueryHandler
import com.onion.core.platformhub.handler.IPluginSubscribeHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import org.json.JSONObject
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: AppPlugin
 * Author: admin by 张琦
 * Date: 2024/6/29 15:19
 * Description:
 */
@Singleton
class AppPlugin @Inject constructor(assetsLoader: AssetsLoader): Plugin(assetsLoader) {

    @Inject
    lateinit var pluginQueryHandlerList: Set<@JvmSuppressWildcards IPluginQueryHandler>

    @Inject
    lateinit var pluginSubscribeHandlerList: Set<@JvmSuppressWildcards IPluginSubscribeHandler>

    override val policyFileName: String
        get() = "app-plugin-policy.json"

    override fun init(context: Context, configurationFiles: Map<String, InputStream>) {

    }

    override fun onReceive(message: BaseMessage.Query): JSONObject {
        val handler = pluginQueryHandlerList.find {
            it.messageName == message.messageName && it.pluginName == message.pluginName
        }

        return handler?.query(message) ?: JSONObject()
    }

    override fun publish(eventStream: BaseMessage.EventStream): SharedFlow<EventStreamOutput> {
        val handler = pluginSubscribeHandlerList.find {
            it.messageName == eventStream.messageName && it.pluginName == eventStream.pluginName
        }

        return handler?.subscribe(eventStream) ?: MutableSharedFlow()
    }
}