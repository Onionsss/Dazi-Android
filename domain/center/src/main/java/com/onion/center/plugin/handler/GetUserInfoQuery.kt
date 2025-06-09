package com.onion.center.plugin.handler

import com.onion.center.protocol.AppConstant
import com.onion.center.repository.UserRepository
import com.onion.core.common.di.DaziCoroutineScope
import com.onion.core.common.di.IoDispatcher
import com.onion.core.platformhub.core.models.BaseMessage
import com.onion.core.platformhub.core.models.EventStreamOutput
import com.onion.core.platformhub.extra.pluginResultTransfer
import com.onion.core.platformhub.handler.IPluginSubscribeHandler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Copyright (C), 2023-2025 Meta
 * FileName: GetUserInfoQuery
 * Author: admin by 张琦
 * Date: 2025/1/9 11:51
 * Description:
 */
@Singleton
class GetUserInfoQuery @Inject constructor(
    private val userRepository: UserRepository,
    @DaziCoroutineScope val coroutineScope: CoroutineScope,
    @IoDispatcher val dispatcher: CoroutineDispatcher
): IPluginSubscribeHandler {

    override fun subscribe(eventStream: BaseMessage.EventStream): SharedFlow<EventStreamOutput> {
        val flow = MutableSharedFlow<EventStreamOutput>()
        coroutineScope.launch(dispatcher) {
            val json = userRepository.getUserInfo().pluginResultTransfer()
            val out = EventStreamOutput(json)
            flow.emit(out)
        }
        return flow
    }


    override val messageName: String
        get() = "GetUserInfo"

    override val pluginName: String
        get() = AppConstant.Plugin.AppPlugin

}