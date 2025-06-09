package com.onion.core.platformhub.core

import android.content.Context
import com.onion.core.platformhub.core.models.BaseMessage
import com.onion.core.platformhub.core.models.EventStreamOutput
import com.onion.core.platformhub.policy.Policy
import com.onion.core.platformhub.policy.model.PrivacySchemaModel
import com.onion.core.platformhub.core.util.AssetsLoader
import kotlinx.coroutines.flow.SharedFlow
import org.json.JSONObject
import java.io.InputStream

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: Plugin
 * Author: admin by 张琦
 * Date: 2024/6/23 23:45
 * Description:
 */
abstract class Plugin(private val assetsLoader: AssetsLoader) {

    abstract val policyFileName: String

    open val policy: Policy by lazy { assetsLoader.loadPolicyFile(policyFileName) }
    open val privacySchemas: List<PrivacySchemaModel> by lazy { assetsLoader.loadPrivacySchemaFiles() }

    lateinit var messageSender: MessageSender
        private set

    fun init(
        context: Context,
        messageSender: MessageSender,
        configurationFiles: Map<String,InputStream>
    ){
        this.messageSender = messageSender
        init(context,configurationFiles)
    }

    abstract fun init(context: Context,configurationFiles: Map<String,InputStream>)

    open fun postMicroKernelInit(){
        // No-op
    }

    open fun publish(eventStream: BaseMessage.EventStream): SharedFlow<EventStreamOutput>{
        TODO("publish")
    }

    open fun subscribeOnStartup(
        subscription: SharedFlow<EventStreamOutput>,
        eventStream: BaseMessage.EventStream
    ){
        TODO("subscribeOnStartup")
    }

    open fun onReceive(message: BaseMessage.Command){
        TODO("onReceive Command")
    }

    open fun onReceive(message: BaseMessage.Query): JSONObject {
        TODO("onReceive Query")
    }

    fun name(): String = policy.name

    fun simpleName(): String? = this::class.simpleName
}