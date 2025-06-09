package com.onion.feature.home.di

import com.onion.core.common.di.DefaultHttp
import com.onion.feature.home.api.HomeApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: ApiModule
 * Author: admin by 张琦
 * Date: 2024/6/29 14:18
 * Description:
 */
@Module
@InstallIn(SingletonComponent::class)
internal object ApiModule {

    @Singleton
    @Provides
    fun providerHomeService(@DefaultHttp retrofit: Retrofit): HomeApiService{
        return retrofit.create(HomeApiService::class.java)
    }

}