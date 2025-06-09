package com.onion.feature.home.repository

import com.onion.core.common.http.HttpState
import com.onion.core.platformhub.core.models.EventStreamOutput
import com.onion.feature.home.model.HomeBannerModel
import com.onion.feature.home.model.HomeCityModel
import com.onion.feature.home.model.HomeHotModel
import com.onion.feature.home.model.HomeKingKongModel
import com.onion.feature.home.model.HomeListModel
import kotlinx.coroutines.flow.SharedFlow

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: HomeRepository
 * Author: admin by 张琦
 * Date: 2024/7/28 14:52
 * Description:
 */
internal interface HomeRepository {

    suspend fun getHomeBanner(): HttpState<HomeBannerModel?>

    suspend fun getHomeKingKong(): HttpState<HomeKingKongModel?>

    suspend fun getHomeHot(): HttpState<HomeHotModel?>

    suspend fun getHomeList(): HttpState<HomeListModel?>

    suspend fun getUserInfo(): SharedFlow<EventStreamOutput>

    suspend fun getCity(): HttpState<HomeCityModel>
}