package com.onion.feature.home.redux.refresh

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: HomeRefreshAction
 * Author: admin by 张琦
 * Date: 2024/11/1 18:12
 * Description:
 */
internal sealed class HomeRefreshAction {
    data object Refresh: HomeRefreshAction()
}