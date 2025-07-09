package com.onion.core.ext

/**
 * author : Qi Zhang
 * date : 2025/7/9
 * description :
 */
fun String.limitWithEllipsis(limit: Int): String {
    return if (this.length > limit) this.take(limit) + "…" else this
}