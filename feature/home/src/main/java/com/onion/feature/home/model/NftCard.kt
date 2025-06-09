package com.onion.feature.home.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: NftCard
 * Author: admin by 张琦
 * Date: 2024/12/3 17:47
 * Description:
 */

/**
 * nft发行商
 */
@Parcelize
internal data class NftPublisher(
    val imageUrl: String,
    val name: String,
    val description: String,
): Parcelable{

}

/**
 * nft系列
 */
@Parcelize
internal data class NftSeries(
    val id: Int
): Parcelable{

}

/**
 * nft卡片
 */
@Parcelize
internal data class NftCard(
    val id: Int,
    val imageUrl: String,
    val name: String,
    val chain: String,
): Parcelable {
}