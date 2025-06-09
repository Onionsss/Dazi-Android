package com.onion.core.common.http

import kotlinx.serialization.Serializable

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: BaseResult
 * Author: admin by 张琦
 * Date: 2024/3/3 18:52
 * Description:
 */
@Serializable
data class BaseResult<T>(
    val code: Int,
    val info: String,
    val data: T?,
    var jsonObj: String? = null
): HttpWrapper<T> {

    override fun getOriginCode(): Int {
        return code
    }

    override fun getOriginMsg(): String {
        return info
    }

    override fun getOriginData(): T? {
        return data
    }

    override fun getOrigin() = jsonObj

}