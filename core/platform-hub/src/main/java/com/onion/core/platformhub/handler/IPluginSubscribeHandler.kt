package com.onion.core.platformhub.handler

import com.onion.core.platformhub.core.models.BaseMessage
import com.onion.core.platformhub.core.models.EventStreamOutput
import kotlinx.coroutines.flow.SharedFlow

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: IPluginSubscribeHandler
 * Author: admin by 张琦
 * Date: 2024/6/29 15:12
 * Description:
 */
interface IPluginSubscribeHandler: IPluginHandler{

    fun subscribe(eventStream: BaseMessage.EventStream): SharedFlow<EventStreamOutput>

}