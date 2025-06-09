package com.onion.feature.home.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: HomeHotModel
 * Author: admin by 张琦
 * Date: 2024/11/28 20:19
 * Description:
 */
@Parcelize
internal data class HomeHotModel(
    val hotList: ArrayList<HomeHotItem>
): Parcelable {
}

@Parcelize
internal data class HomeHotItem(
    val imageUrl: String,
    val name: String
): Parcelable {
}
