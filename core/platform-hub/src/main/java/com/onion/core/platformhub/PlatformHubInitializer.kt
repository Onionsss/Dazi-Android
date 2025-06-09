package com.onion.core.platformhub

import com.onion.core.platformhub.core.Plugin
import java.io.InputStream

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: PlatformHubInitializer
 * Author: admin by 张琦
 * Date: 2024/6/17 23:18
 * Description:
 */
interface PlatformHubInitializer {

    val plugins: List<Pair<Plugin,Map<String, InputStream>>>

}