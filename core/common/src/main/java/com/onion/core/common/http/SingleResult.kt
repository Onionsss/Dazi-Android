package com.onion.core.common.http

import kotlinx.serialization.Serializable

/**
 * author : Qi Zhang
 * date : 2025/7/10
 * description :
 */
@Serializable
data class SingleResult<T>(
    val data: T
) {
}