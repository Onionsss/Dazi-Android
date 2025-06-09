package com.onion.center.plugin

import javax.inject.Inject

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: PluginProviderImpl
 * Author: admin by 张琦
 * Date: 2024/6/29 15:14
 * Description:
 */
class PluginProviderImpl @Inject constructor(
    private val plugins: Set<PluginProvider.PluginEntry>
) : PluginProvider {

    override fun getPluginsList(): Sequence<PluginProvider.PluginEntry> = plugins.asSequence()
}