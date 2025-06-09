package com.onion.center.di

import android.content.Context
import com.onion.core.platformhub.PlatformHub
import com.onion.core.platformhub.core.util.AssetsLoader
import com.onion.core.platformhub.core.util.AssetsLoaderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @JvmStatic
    @Provides
    fun provideContext(@ApplicationContext application: Context): Context {
        return application
    }

    @Singleton
    @JvmStatic
    @Provides
    fun provideAssetsLoader(context: Context): AssetsLoader = AssetsLoaderImpl(context)

    @JvmStatic
    @Provides
    @Singleton
    fun providesPlatformHub(): PlatformHub = PlatformHub()

}