package com.onion.core.platformhub.core.models

import org.json.JSONObject

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: Message
 * Author: admin by 张琦
 * Date: 2024/6/23 23:24
 * Description:
 */
interface Message {
    val messageName: String
    val payload: JSONObject?
    val pluginName: String
    val uuid: String

    fun correlatesWith(otherMessage: Message?) = uuid == otherMessage?.uuid
}