package com.onion.feature.home.redux.refresh

import kotlinx.serialization.Serializable

/**
* Copyright (C), 2023-2024 Meta
* FileName: HomeRefreshState
* Author: admin by 张琦
* Date: 2024/11/1 18:11
* Description: 
*/
@Serializable
internal data class HomeRefreshState(
    val refresh: Boolean = false,
    val version: Int = 0
) {

}