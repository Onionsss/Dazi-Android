package com.onion.feature.home.di

import com.onion.feature.home.repository.HomeRepository
import com.onion.feature.home.repository.HomeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: RepositoryModule
 * Author: admin by 张琦
 * Date: 2024/7/28 14:53
 * Description:
 */
@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun providerHomeRepository(impl: HomeRepositoryImpl): HomeRepository

}