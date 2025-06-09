package com.onion.login.api

import com.onion.core.common.http.BaseResult
import com.onion.login.bean.LoginResp
import retrofit2.http.POST

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: LoginService
 * Author: admin by 张琦
 * Date: 2024/6/29 13:43
 * Description:
 */
interface LoginApiService {

    @POST("/api/v1/user/login")
    suspend fun login(): BaseResult<LoginResp>

    @POST("/api/v1/user/register")
    suspend fun register(): BaseResult<LoginResp>
}