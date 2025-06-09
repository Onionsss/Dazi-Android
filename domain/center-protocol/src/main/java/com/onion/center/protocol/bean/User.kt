package com.onion.center.protocol.bean

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

/**
 * Copyright (C), 2023-2025 Meta
 * FileName: User
 * Author: admin by 张琦
 * Date: 2025/1/9 11:42
 * Description:
 */
@Parcelize
@Serializable
data class User(
    val id: String? = null,
    val phone: String? = null,
    val nickname: String? = null,
    val avatar: String? = null,
    val gender: Int = 1,
    val star: Int = 0,
): Parcelable {
}