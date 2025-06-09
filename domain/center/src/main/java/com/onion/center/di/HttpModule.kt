package com.onion.center.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.onion.center.api.HeaderInterceptor
import com.onion.center.api.UserApiService
import com.onion.core.common.di.DefaultHttp
import com.onion.core.common.http.NetConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
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
object HttpModule {

    @Provides
    @DefaultHttp
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Provides
    @DefaultHttp
    @Singleton
    fun providerHeaderInterceptor(headerInterceptor: HeaderInterceptor): HeaderInterceptor{
        return headerInterceptor
    }

    @Singleton
    @DefaultHttp
    @Provides
    fun providerOkhttp(@DefaultHttp headerInterceptor: HeaderInterceptor): OkHttpClient{
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient: OkHttpClient = OkHttpClient
            .Builder()
            .connectTimeout(60L,TimeUnit.SECONDS)
            .readTimeout(60L,TimeUnit.SECONDS)
            .writeTimeout(60L,TimeUnit.SECONDS)
            .followRedirects(false)
            .addInterceptor(headerInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()
        return okHttpClient
    }

    @Singleton
    @DefaultHttp
    @Provides
    fun providerRetrofit(@DefaultHttp okHttpClient: OkHttpClient,@DefaultHttp config: NetConfig,@DefaultHttp json: Json): Retrofit{
        val retrofit = Retrofit.Builder()
            .baseUrl(config.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
        return retrofit
    }

    @Singleton
    @Provides
    fun providerUserApiService(@DefaultHttp retrofit: Retrofit): UserApiService{
        return retrofit.create(UserApiService::class.java)
    }
}