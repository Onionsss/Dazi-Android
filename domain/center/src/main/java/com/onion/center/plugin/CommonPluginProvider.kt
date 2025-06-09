package com.onion.center.plugin

import javax.inject.Inject

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: CommonPluginProvider
 * Author: admin by 张琦
 * Date: 2024/6/29 15:15
 * Description:
 */
class CommonPluginProvider @Inject constructor(): PluginProvider {

    override fun getPluginsList(): Sequence<PluginProvider.PluginEntry> = emptySequence()
}