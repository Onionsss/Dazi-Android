package com.onion.feature.home.api

import com.onion.core.common.http.HttpWrapper
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: RepositoryModule
 * Author: admin by 张琦
 * Date: 2024/7/28 14:53
 * Description:
 */
internal interface HomeApiService{

    @GET("")
    suspend fun getHomeBanner(): HttpWrapper<String>

    @POST("/api/v1//user/getUserInfo")
    suspend fun getUserInfo(): HttpWrapper<String>
}
