package com.onion.center.di

import com.onion.center.protocol.BaseConfig
import com.onion.core.common.di.DefaultHttp
import com.onion.core.common.http.NetConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: NetModel
 * Author: admin by 张琦
 * Date: 2024/5/20 21:56
 * Description:
 */
@Module
@InstallIn(SingletonComponent::class)
object ConfigModule {

    @Provides
    @Singleton
    @DefaultHttp
    fun providerNetConfig(): NetConfig {
        return NetConfig(
            "http://192.168.0.106:8002/"
        )
    }

    @Provides
    @Singleton
    fun providerBaseConfig(): BaseConfig{
        return BaseConfig(1)
    }
}