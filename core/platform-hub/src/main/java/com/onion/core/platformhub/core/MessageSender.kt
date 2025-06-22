package com.onion.core.platformhub.core

import com.onion.core.platformhub.core.models.BaseMessage
import com.onion.core.platformhub.core.models.EventStreamOutput
import com.onion.core.platformhub.core.models.Message
import kotlinx.coroutines.flow.SharedFlow
import org.json.JSONObject

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: MessageSerder
 * Author: admin by 张琦
 * Date: 2024/6/23 23:49
 * Description:
 */
interface MessageSender {

    fun subscribeOnDemand(
        eventStream: BaseMessage.EventStream,
        subscribe: Plugin
    ): SharedFlow<EventStreamOutput>

    fun send(command: BaseMessage.Command,plugin: Plugin)

    fun send(query: BaseMessage.Query,plugin: Plugin): JSONObject

    fun getReceiverForNavigation(
        message: Message,
        sender: Plugin
    ): Plugin

    fun getReceiverForNavigationList(
        message: Message,
        sender: Plugin
    ): ArrayList<Plugin>
}