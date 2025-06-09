package com.onion.center.plugin

import com.onion.core.platformhub.core.Plugin
import java.io.InputStream

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: PluginProvider
 * Author: admin by 张琦
 * Date: 2024/6/29 15:14
 * Description:
 */
interface PluginProvider {
    fun getPluginsList(): Sequence<PluginEntry>

    /**
     * PlatformHub Plugin with configuration
     *
     * @property plugin - PlatformHub plugin
     * @property configurations - Optional PlatformHub configuration, emptyMap by default
     * @property shouldAddToPlatformHub - Lazy delegate if this plugin should be added to PlatformHub.
     * Useful when some plugins should be added only to a debug app. Returns true by default.
     */
    data class PluginEntry(
        val plugin: Plugin,
        val configurations: Map<String, InputStream> = emptyMap(),
        val shouldAddToPlatformHub: () -> Boolean = { true }
    )
}