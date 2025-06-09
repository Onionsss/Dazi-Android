package com.onion.core.platformhub.model

import com.onion.core.platformhub.core.models.BaseMessage
import org.json.JSONObject

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: PluginSender
 * Author: admin by 张琦
 * Date: 2024/6/29 15:06
 * Description:
 */
interface PluginSender {

    val pluginName: String

    open fun query(messageName: String, payload: JSONObject? = null): BaseMessage.Query{
        return BaseMessage.Query(messageName, pluginName,payload)
    }

    open fun command(messageName: String, payload: JSONObject? = null): BaseMessage.Command{
        return BaseMessage.Command(messageName, pluginName,payload)
    }

    open fun stream(messageName: String, payload: JSONObject? = null): BaseMessage.EventStream{
        return BaseMessage.EventStream(messageName,pluginName,payload)
    }
}