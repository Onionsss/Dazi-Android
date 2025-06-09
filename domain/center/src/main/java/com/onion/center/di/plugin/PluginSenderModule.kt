package com.onion.center.di.plugin

import com.onion.center.protocol.AppConstant
import com.onion.core.platformhub.model.PluginSender
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PluginSenderModule {

    @Provides
    @Singleton
    @Named(AppConstant.Plugin.AppPlugin)
    fun providerAppSender(): PluginSender{
        return object: PluginSender{
            override val pluginName: String
                get() = AppConstant.Plugin.AppPlugin
        }
    }

    @Provides
    @Singleton
    @Named(AppConstant.Plugin.HomePlugin)
    fun providerHomeSender(): PluginSender{
        return object: PluginSender{
            override val pluginName: String
                get() = AppConstant.Plugin.HomePlugin
        }
    }

}