package com.onion.center.di.plugin

import com.onion.center.plugin.AppPlugin
import com.onion.center.plugin.createPluginEntry
import com.onion.feature.home.plugin.HomePlugin
import com.onion.login.plugin.LoginPlugin
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PluginModule {

    @Provides
    @Singleton
    @IntoSet
    fun providesAppPlugin(appPlugin: AppPlugin) =
        createPluginEntry(appPlugin)

    @Provides
    @Singleton
    @IntoSet
    fun providesLoginPlugin(
        loginPlugin: LoginPlugin
    ) =
        createPluginEntry(
            loginPlugin
        )

    @Provides
    @Singleton
    @IntoSet
    fun providesHomePlugin(
        homePlugin: HomePlugin
    ) =
        createPluginEntry(
            homePlugin
        )
}