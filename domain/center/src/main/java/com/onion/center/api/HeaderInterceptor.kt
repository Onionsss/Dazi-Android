package com.onion.center.api

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: HeaderInterceptor
 * Author: admin by 张琦
 * Date: 2024/7/28 15:04
 * Description:
 */
@Singleton
class HeaderInterceptor @Inject constructor(

): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()

        request.addHeader("Authorization",TEMP_TOKEN)
        return chain.proceed(request.build())
    }
}

private const val TEMP_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwaG9uZSI6IjEzMjcwNjY1NzAyIiwiYXZhdGFyIjoiYXZhdG9yLzAxODY5NTc5LTJhZDgtNGM4Yi04ZDI3LWVmOGFhYWIzZDlkOeW-ruS_oeWbvueJh18yMDI0MDExMDIxMTI1OS5qcGciLCJleHAiOjE3Mzc4NjQ5MTIsInVzZXJJZCI6MTgxNzU0MDY5NzE0Njc4OTg4OX0.OiV3GYCly0tL8aSCvaHSvuoqlCf8DInWd3QSvjJ7W4k"