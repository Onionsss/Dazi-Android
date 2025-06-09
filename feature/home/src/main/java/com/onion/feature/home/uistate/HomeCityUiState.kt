package com.onion.feature.home.uistate

import com.onion.feature.home.model.HomeCityModel

/**
 * Copyright (C), 2023-2025 Meta
 * FileName: HomeCityUiState
 * Author: admin by 张琦
 * Date: 2025/1/15 12:47
 * Description:
 */
internal sealed class HomeCityUiState {

    data object Loading: HomeCityUiState()

    data class Success(val model: HomeCityModel?): HomeCityUiState()
}