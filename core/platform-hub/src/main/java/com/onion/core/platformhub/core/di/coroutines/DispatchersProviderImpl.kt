package com.onion.core.platformhub.core.di.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: DispatchersProvider
 * Author: admin by 张琦
 * Date: 2024/6/17 23:38
 * Description:
 */
class DispatchersProviderImpl: DispatchersProvider {

    override fun provideIODispatcher() = Dispatchers.IO

    override fun provideDefaultDispatcher() = Dispatchers.Default

    override fun provideMainDispatcher() = Dispatchers.Main

}