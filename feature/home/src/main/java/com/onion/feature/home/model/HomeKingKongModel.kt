package com.onion.feature.home.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: HomeKingKongModel
 * Author: admin by 张琦
 * Date: 2024/11/28 19:57
 * Description:
 */
@Parcelize
internal data class HomeKingKongModel(
    val list: ArrayList<HomeKingKongItem>
): Parcelable {
}

@Parcelize
internal data class HomeKingKongItem(
    val name: String,
    val imageUrl: String,
): Parcelable {
}