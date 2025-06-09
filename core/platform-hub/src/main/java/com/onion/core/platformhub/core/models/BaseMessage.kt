package com.onion.core.platformhub.core.models

import org.json.JSONObject
import java.util.UUID

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: BaseMessage
 * Author: admin by 张琦
 * Date: 2024/6/23 23:24
 * Description:
 */
sealed class BaseMessage(
    override val messageName: String,
    override val payload: JSONObject? = null,
    override val pluginName: String,
    override val uuid: String = UUID.randomUUID().toString()
): Message {

    data class EventStream(
        override val messageName: String,
        override val pluginName: String,
        override val payload: JSONObject? = null
    ): BaseMessage(
        messageName = messageName,
        pluginName = pluginName
    )

    data class Command(
        override val messageName: String,
        override val pluginName: String,
        override val payload: JSONObject? = null
    ): BaseMessage(
        messageName = messageName,
        payload = payload,
        pluginName = pluginName
    )

    data class Query(
        override val messageName: String,
        override val pluginName: String,
        override val payload: JSONObject? = null
    ): BaseMessage(
        messageName = messageName,
        payload = payload,
        pluginName = pluginName
    )
}