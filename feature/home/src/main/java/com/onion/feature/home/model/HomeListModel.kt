package com.onion.feature.home.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: HomeListModel
 * Author: admin by 张琦
 * Date: 2024/12/3 17:51
 * Description:
 */
@Parcelize
internal data class HomeListModel(
    val id: Int,
    val list: ArrayList<HomeListItem>
) : Parcelable {

}

@Parcelize
internal open class HomeListItem(
    val type: Int,
    ) : Parcelable {

    companion object {
        const val NEW = 1 // 最新推出
        const val SINGLE_CARD = 2//单个卡片
        const val TWO_CARD = 3//单个卡片
        const val CARD_AND_FOUR_PUBLISHER = 4 //card和四个供应商
        const val SINGLE_PUBLISH = 5//供应商滚动
        const val PUBLISHER_SCROLL = 6//供应商滚动
        const val PUBLISHER_FIXED = 7//供应商滚动
        const val DAY_CHOOSE = 8//每日精选
    }

}

@Parcelize
internal data class HomeListNew(
    val topTitle: String,
    val imageUrl: String,
    val tag: String,
    val title: String,
    val subTitle: String,
    val publisher: NftPublisher,
    val primaryColor: Long,
    val primarySubColor: Long
) : HomeListItem(NEW) {

}

@Parcelize
internal data class HomeListSinglePublisher(
    val imageUrl: String,
    val title: String,
    val tag: String,
    val subTitle: String,
    val publisher: NftPublisher
) : HomeListItem(SINGLE_PUBLISH) {

}

@Parcelize
internal data class HomeListTwoCard(
   val list: ArrayList<HomeListTwoCardItem>
) : HomeListItem(TWO_CARD) {

}

@Parcelize
internal data class HomeListTwoCardItem(
    val imageUrl: String,
    val topTitle: String,
    val title: String,
    val primaryColor: Long,
    val primarySubColor: Long
) : Parcelable

@Parcelize
internal data class HomeListSingleCard(
    val imageUrl: String,
    val topTitle: String,
    val topSubTitle: String,
    val tag: String,
    val title: String,
    val subTitle: String,
    val primaryColor: Long,
    val primarySubColor: Long
) : HomeListItem(SINGLE_CARD)

@Parcelize
internal data class HomeFourPublisherCard(
    val imageUrl: String,
    val publishers: ArrayList<NftPublisher>
) : HomeListItem(CARD_AND_FOUR_PUBLISHER)

/**
 * 滚动
 */
@Parcelize
internal data class HomePublisherScrollCard(
    val imageUrl: String,
    val tag: String,
    val title: String,
    val publishers: ArrayList<HomeScrollMix>,
) : HomeListItem(PUBLISHER_SCROLL)

@Parcelize
internal data class HomeScrollMix(
    val nftPublisher: NftPublisher? = null,
    val nftCard: NftCard? = null
): Parcelable{

}


@Parcelize
internal data class HomeDayChooseCard(
    val imageUrl: String,
    val topTitle: String,
    val tag: String,
    val title: String,
    val publishers: ArrayList<NftPublisher>,
) : HomeListItem(DAY_CHOOSE)
