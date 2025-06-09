package com.onion.feature.home.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: HomeBannerModel
 * Author: admin by 张琦
 * Date: 2024/11/28 14:31
 * Description:
 */
@Parcelize
internal data class HomeBannerModel(
    val list: ArrayList<HomeBannerItem>
): Parcelable {
}

@Parcelize
internal data class HomeBannerItem(
    val id: Int,
    val imageUrl: String
): Parcelable {
}

