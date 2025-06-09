package com.onion.core.common.http

import com.onion.core.common.exceptions.AppException

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: HttpState
 * Author: admin by 张琦
 * Date: 2024/6/29 13:55
 * Description:
 */

sealed class HttpState<out T>(){

    data class Loading(val loadingMessage: String? = null): HttpState<Nothing>()

    data class Success<out T>(val data: T?): HttpState<T>()

    data class Failed<T>(val data: HttpWrapper<T>): HttpState<T>()

    data class Error(val error: AppException): HttpState<Nothing>()

    data object None: HttpState<Nothing>()

    companion object {
        fun <T> onSuccess(data: T?): HttpState<T> =
            Success(data)

        fun <T> onFailed(data: HttpWrapper<T>): HttpState<T> =
            Failed(data)

        fun <T> onError(throwable: AppException): HttpState<T> =
            Error(throwable)

        fun <T> onLoading(message: String = ""): HttpState<T> =
            Loading(message)
    }


}
