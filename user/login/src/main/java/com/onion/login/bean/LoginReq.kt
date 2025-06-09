package com.onion.login.bean

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: LoginReq
 * Author: admin by 张琦
 * Date: 2024/6/29 13:45
 * Description:
 */
data class LoginReq(
    val phone: String,
    val password: String? = null,
    val verifyCode: String? = null,
) {
}

data class LoginResp(
    val token: String,
)