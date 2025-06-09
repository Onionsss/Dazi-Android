package com.onion.core.platformhub.core.models

import com.onion.core.platformhub.core.extensions.toTimestamp
import org.json.JSONObject

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: LogInfo
 * Author: admin by 张琦
 * Date: 2024/6/29 14:30
 * Description:
 */
sealed class LogInfo(
    open val messageType: MessageType,
    open val messageName: String,
    open val payload: JSONObject?
) {
    val timestamp: String = System.currentTimeMillis().toTimestamp()

    data class MessageInfo(
        val senderPlugin: String,
        val receiverPlugin: String,
        override val messageType: MessageType,
        override val messageName: String,
        override val payload: JSONObject?,
        val responsePayload: JSONObject?
    ) : LogInfo(
        messageType = messageType,
        messageName = messageName,
        payload = payload
    )

    data class EventStreamInfo(
        val publisherPlugin: String,
        val subscriberPlugins: List<String>,
        override val messageName: String,
        override val payload: JSONObject?,
        val resultPayload: Result<JSONObject>? = null
    ) : LogInfo(
        messageType = MessageType.EventStream,
        messageName = messageName,
        payload = payload
    )

    data class ErrorInfo(
        val senderPlugin: String,
        val receiverPlugin: String,
        val errorMessage: String,
        override val messageType: MessageType,
        override val messageName: String,
        override val payload: JSONObject?
    ) : LogInfo(
        messageType = messageType,
        messageName = messageName,
        payload = payload
    )

    data class InternalPluginAction(
        val pluginName: String,
        override val messageName: String,
        val screenName: String = "",
        override val payload: JSONObject?
    ) : LogInfo(
        messageType = MessageType.InternalPluginAction,
        messageName = messageName,
        payload = payload
    )
}

enum class MessageType {
    EventStream, Command, NavigationQuery, Query, InternalPluginAction
}