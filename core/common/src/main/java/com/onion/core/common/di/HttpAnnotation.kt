package com.onion.core.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: HttpModule
 * Author: admin by 张琦
 * Date: 2024/6/29 14:19
 * Description:
 */

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DefaultHttp