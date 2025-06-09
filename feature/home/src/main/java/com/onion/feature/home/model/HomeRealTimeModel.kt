package com.onion.feature.home.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: HomeRealTimeModel
 * Author: admin by 张琦
 * Date: 2024/11/29 18:23
 * Description:
 */
@Parcelize
internal data class HomeRealTimeModel(
    val version: Int
): Parcelable {
}