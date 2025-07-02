package com.onion.core.common.http.ext

import com.onion.core.common.exceptions.AppException
import com.onion.core.common.http.BaseResult
import com.onion.core.common.http.HttpState
import com.onion.core.common.http.HttpWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: HttpStateExt
 * Author: admin by 张琦
 * Date: 2024/6/29 14:03
 * Description:
 */
fun HttpState<*>.isSuccess(): Boolean{
    return this is HttpState.Success
}

fun HttpState<*>.hasData(): Boolean{
    return isSuccess() && (this as HttpState.Success).data != null
}

fun <T> HttpState<T>.getSuccessData(failed: ((HttpWrapper<T>?) -> Unit)? = null): T?{
    if(!hasData()){
        val wrapper = when{
            this is HttpState.Failed -> {
                this.data
            }
            else -> {
                null
            }
        }
        failed?.invoke(wrapper)
    }else{
        val success = this as HttpState.Success<T>
        return success.data
    }
    return null
}

suspend fun <T> syncCallHttpState(
    dispatcher: CoroutineContext = Dispatchers.IO,
    block: suspend () -> BaseResult<T>
): HttpState<T?> {
    return try {
        HttpState.onLoading<Nothing>()
        val result = withContext(dispatcher){
            block.invoke()
        }
        if (result.isSuccess()) {
            HttpState.onSuccess(result.data)
        } else {
            HttpState.onFailed(result)
        }
    } catch (e: Exception) {
        HttpState.onError(AppException(e))
    }
}