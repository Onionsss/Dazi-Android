package com.onion.core.common.http

import com.onion.core.constants.http.HttpConstant

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: HttpWrapper
 * Author: admin by 张琦
 * Date: 2024/3/3 18:51
 * Description:
 */
interface HttpWrapper<T> {

    fun getOriginCode(): Int

    fun getOriginMsg(): String

    fun getOriginData(): T?

    fun getOrigin(): String?

    fun success(): Boolean{
        return getOriginCode() == HttpConstant.Success.OK
    }

    fun failed(): Boolean{
        return HttpConstant.isFailedCode(getOriginCode())
    }
}