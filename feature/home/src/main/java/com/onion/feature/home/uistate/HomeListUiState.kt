package com.onion.feature.home.uistate

import com.onion.feature.home.model.HomeListModel

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: HomeBannerUiState
 * Author: admin by 张琦
 * Date: 2024/11/28 14:51
 * Description:
 */
internal sealed class HomeListUiState {

    data object Loading: HomeListUiState()

    data class Success(val model: HomeListModel): HomeListUiState()
}