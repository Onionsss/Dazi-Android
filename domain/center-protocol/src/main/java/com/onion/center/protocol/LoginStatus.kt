package com.onion.center.protocol

import kotlinx.serialization.Serializable

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: LoginStatus
 * Author: admin by 张琦
 * Date: 2024/7/4 22:46
 * Description:
 */
@Serializable
data class LoginStatus(
    val isLogin: Boolean,
) {
}