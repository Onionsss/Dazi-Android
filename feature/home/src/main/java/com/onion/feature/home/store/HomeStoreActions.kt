package com.onion.feature.home.store

import com.onion.feature.home.redux.refresh.HomeRefreshAction

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: HomeStoreActions
 * Author: admin by 张琦
 * Date: 2024/11/1 18:13
 * Description:
 */
internal sealed class HomeStoreActions {
    data class HomeRefreshActions(val action: HomeRefreshAction): HomeStoreActions()
}