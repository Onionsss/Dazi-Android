package com.onion.feature.home.uistate

import com.onion.center.protocol.bean.User

/**
 * Copyright (C), 2023-2025 Meta
 * FileName: CommonUiState
 * Author: admin by 张琦
 * Date: 2025/1/9 12:35
 * Description:
 */
internal sealed class UserInfoUiState {

    data object Loading: UserInfoUiState()

    data class Success(val model: User?): UserInfoUiState()
}