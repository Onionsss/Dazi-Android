package com.onion.core.platformhub.core.di.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: CoroutineScopeProvider
 * Author: admin by 张琦
 * Date: 2024/6/17 23:37
 * Description:
 */
class CoroutineScopeProvider(coroutineDispatcher: CoroutineDispatcher) {

    private val context = SupervisorJob() + coroutineDispatcher

    fun getCoroutineScope() = CoroutineScope(context)

    fun cancel(){
        context.cancel()
    }

}