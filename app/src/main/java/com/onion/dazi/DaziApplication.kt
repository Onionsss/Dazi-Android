package com.onion.dazi

import android.content.Context
import com.onion.center.CenterApplication
import com.onion.center.plugin.PlatformHubInitializerImpl
import com.onion.core.platformhub.PlatformHub
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * Copyright (C), 2023-2023 Meta
 * FileName: NFTApplication
 * Author: admin by 张琦
 * Date: 2023/4/10 22:01
 * Description:
 *
 * ollama
 * lobechat
 */
@HiltAndroidApp
class DaziApplication: CenterApplication() {

    @Inject
    internal lateinit var context: Context

    @Inject
    internal lateinit var platformHub: PlatformHub

    @Inject
    lateinit var platformImpl: PlatformHubInitializerImpl

    override fun onCreate() {
        super.onCreate()
        platformHub.init(this.applicationContext,platformImpl)
    }

}