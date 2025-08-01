package com.onion.login.plugin

import android.content.Context
import com.onion.core.platformhub.core.Plugin
import com.onion.core.platformhub.core.models.BaseMessage
import com.onion.core.platformhub.core.util.AssetsLoader
import com.onion.core.platformhub.handler.IPluginCommandHandler
import com.onion.core.platformhub.handler.IPluginQueryHandler
import com.onion.core.redux.Store
import org.json.JSONObject
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: LoginPlugin
 * Author: admin by 张琦
 * Date: 2024/6/29 15:25
 * Description:
 */
@Singleton
class LoginPlugin @Inject constructor(assetsLoader: AssetsLoader): Plugin(assetsLoader) {

    @Inject
    lateinit var pluginQueryHandlerList: Set<@JvmSuppressWildcards IPluginQueryHandler>

//    @Inject
//    lateinit var pluginCommandHandlerList: Set<@JvmSuppressWildcards IPluginCommandHandler>

    override val policyFileName: String
        get() = "login-plugin-policy.json"

    override fun init(context: Context, configurationFiles: Map<String, InputStream>) {

    }

    override fun onReceive(message: BaseMessage.Query): JSONObject {
        val handler = pluginQueryHandlerList.find {
            it.messageName == message.messageName && it.pluginName == message.pluginName
        }

        return handler?.query(message) ?: JSONObject()
    }

//    override fun onReceive(message: BaseMessage.Command) {
//        val handler = pluginCommandHandlerList.find {
//            it.messageName == message.messageName && it.pluginName == message.pluginName
//        }
//
//        handler?.command(message)
//        super.onReceive(message)
//    }

    companion object{

    }
}