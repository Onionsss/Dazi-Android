package com.onion.feature.home.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: HomeCityModel
 * Author: admin by 张琦
 * Date: 2024/11/28 19:32
 * Description:
 */
@Parcelize
internal data class HomeCityModel(
    val cityName: String,
    val avatar: String,
    val today: String,
    val todayDate: String,
): Parcelable {
}