package com.onion.feature.home.uistate

import com.onion.feature.home.model.HomeRealTimeModel

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: HomeBannerUiState
 * Author: admin by 张琦
 * Date: 2024/11/28 14:51
 * Description:
 */
internal sealed class HomeRealTimeUiState {

    data object Loading: HomeRealTimeUiState()

    data class Success(val model: HomeRealTimeModel): HomeRealTimeUiState()
}