package com.onion.core.platformhub

import android.content.Context
import com.onion.core.platformhub.core.DefaultLogger
import com.onion.core.platformhub.core.Logger
import com.onion.core.platformhub.core.MicroKernel
import com.onion.core.platformhub.core.util.AssetsLoaderImpl

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: PlatformHub
 * Author: admin by 张琦
 * Date: 2024/6/17 23:10
 * Description:
 */
class PlatformHub {

    fun init(
        applicationContext: Context,
        initializer: PlatformHubInitializer,
        logger: Logger = DefaultLogger(AssetsLoaderImpl(applicationContext))
    ){
        val microKernel = MicroKernel(logger)
        microKernel.init(
            applicationContext = applicationContext,
            pluginsWithConfigurations = initializer.plugins
        )
    }
}