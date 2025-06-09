package com.onion.center.api

import com.onion.center.protocol.bean.User
import com.onion.core.common.http.BaseResult
import retrofit2.http.POST

/**
 * Copyright (C), 2023-2025 Meta
 * FileName: UserApiService
 * Author: admin by 张琦
 * Date: 2025/1/9 11:41
 * Description:
 */
interface UserApiService {

    @POST("/api/v1//user/getUserInfo")
    suspend fun getUserInfo(): BaseResult<User>

}