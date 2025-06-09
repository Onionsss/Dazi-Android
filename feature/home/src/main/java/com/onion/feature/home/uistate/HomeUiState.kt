package com.onion.feature.home.uistate

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: HomeUiState
 * Author: admin by 张琦
 * Date: 2024/11/29 10:43
 * Description:
 */
internal sealed class HomeUiState<T> {

    data object Loading: HomeUiState<Nothing>()

    data class Success<T>(val model: T): HomeUiState<T>()

}