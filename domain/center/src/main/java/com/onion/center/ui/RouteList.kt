package com.onion.center.ui

import androidx.navigation.NavGraphBuilder
import com.onion.feature.home.ui.homeRoute

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: RouteList
 * Author: admin by 张琦
 * Date: 2024/11/1 16:43
 * Description:
 */

internal typealias Router = NavGraphBuilder.() -> Unit

val RouteList = arrayListOf<Router>({ homeRoute() })

