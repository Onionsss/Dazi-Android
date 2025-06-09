package com.onion.center.plugin

import com.onion.core.platformhub.PlatformHubInitializer
import com.onion.core.platformhub.core.Plugin
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: PlatformHubInitializerImpl
 * Author: admin by 张琦
 * Date: 2024/6/29 15:15
 * Description:
 */
@Singleton
class PlatformHubInitializerImpl @Inject constructor(
    pluginsProvider: PluginProviderImpl,
    commonPluginProvider: CommonPluginProvider
) : PlatformHubInitializer {
    override val plugins: List<Pair<Plugin, Map<String, InputStream>>> =
        commonPluginProvider.getPluginsList().getEnabledPlugins() + pluginsProvider.getPluginsList()
            .getEnabledPlugins()

    private fun Sequence<PluginProvider.PluginEntry>.getEnabledPlugins() = filter {
        it.shouldAddToPlatformHub()
    }.map {
        it.plugin to it.configurations
    }.toList()
}

fun createPluginEntry(
    plugin: Plugin,
    shouldAddToPlatformHub: () -> Boolean = { true },
    vararg configs: Pair<String, InputStream>
):
        PluginProvider.PluginEntry {
    val configsMap = mutableMapOf<String, InputStream>()
    configs.forEach {
        configsMap[it.first] = it.second
    }

    return PluginProvider.PluginEntry(plugin, configsMap, shouldAddToPlatformHub)
}