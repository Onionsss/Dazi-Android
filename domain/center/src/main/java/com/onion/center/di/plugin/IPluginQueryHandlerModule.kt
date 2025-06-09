package com.onion.center.di.plugin

import com.onion.center.plugin.handler.GetLoginStatusQuery
import com.onion.center.plugin.handler.GetNamePluginQuery
import com.onion.center.plugin.handler.GetUserInfoQuery
import com.onion.core.platformhub.handler.IPluginQueryHandler
import com.onion.core.platformhub.handler.IPluginSubscribeHandler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class IPluginQueryHandlerModule {

    @Binds
    @Singleton
    @IntoSet
    abstract fun providerQueryAppGetName(getNamePluginQuery: GetNamePluginQuery): IPluginQueryHandler

    @Binds
    @Singleton
    @IntoSet
    abstract fun providerQueryAppGetLoginStatus(getLoginStatusQuery: GetLoginStatusQuery): IPluginQueryHandler

    @Binds
    @Singleton
    @IntoSet
    abstract fun providerGetUserInfo(getUserInfo: GetUserInfoQuery): IPluginSubscribeHandler
}