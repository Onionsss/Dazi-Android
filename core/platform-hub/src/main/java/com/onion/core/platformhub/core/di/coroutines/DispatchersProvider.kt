package com.onion.core.platformhub.core.di.coroutines

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: DispatchersProvider
 * Author: admin by 张琦
 * Date: 2024/6/17 23:38
 * Description:
 */
interface DispatchersProvider {

    fun provideIODispatcher(): CoroutineDispatcher

    fun provideDefaultDispatcher(): CoroutineDispatcher

    fun provideMainDispatcher(): CoroutineDispatcher
}