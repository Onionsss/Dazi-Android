package com.onion.core.platformhub.core.logger

import com.onion.core.platformhub.core.Logger
import com.onion.core.platformhub.core.di.coroutines.CoroutineScopeInjector
import com.onion.core.platformhub.core.di.coroutines.CoroutineScopeProvider
import com.onion.core.platformhub.core.di.coroutines.DispatchersProvider
import com.onion.core.platformhub.core.models.EventStreamOutput
import com.onion.core.platformhub.core.models.LogInfo
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: LoggingDecorator
 * Author: admin by 张琦
 * Date: 2024/6/24 0:06
 * Description:
 */
class LoggingDecorator(
    private val decoratedFlow: SharedFlow<EventStreamOutput>,
    private val logger: Logger,
    private val logInfo: LogInfo,
    dispatcherProviderImpl: DispatchersProvider
) : EventFlowProvider {
    private val injector = CoroutineScopeInjector()
    private val scopeProvider: CoroutineScopeProvider =
        injector.inject(dispatcherProviderImpl.provideIODispatcher())

    override fun getSharedFlow(): SharedFlow<EventStreamOutput> {
        scopeProvider.getCoroutineScope().launch {
            decoratedFlow.collectLatest { eventStream ->
                require(logInfo is LogInfo.EventStreamInfo) {
                    throw IllegalStateException("LogInfo is not EventStreamInfo")
                }
                logger.log(logInfo.run {
                    copy(resultPayload = eventStream.result)
                })
            }
        }
        return decoratedFlow
    }
}

interface EventFlowProvider {
    fun getSharedFlow(): SharedFlow<EventStreamOutput>
}