package com.onion.login.di

import com.onion.login.repository.LoginRepository
import com.onion.login.repository.impl.LoginRepositoryImpl
import dagger.Binds
import dagger.BindsInstance
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: RepositoryModule
 * Author: admin by 张琦
 * Date: 2024/6/29 14:15
 * Description:
 */
@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun providerLoginRepository(impl: LoginRepositoryImpl): LoginRepository

}