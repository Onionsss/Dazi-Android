package com.onion.feature.home.store

import com.onion.feature.home.redux.refresh.HomeRefreshState
import kotlinx.serialization.Serializable

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: HomeStoreStates
 * Author: admin by 张琦
 * Date: 2024/11/1 18:05
 * Description:
 */
@Serializable
internal data class HomeStoreStates(
    val homeRefreshState: HomeRefreshState = HomeRefreshState()
) {
}