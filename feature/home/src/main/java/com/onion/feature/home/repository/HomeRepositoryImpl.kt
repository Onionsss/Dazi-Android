package com.onion.feature.home.repository

import android.annotation.SuppressLint
import com.onion.core.common.di.DefaultHttp
import com.onion.core.common.http.HttpState
import com.onion.core.platformhub.core.models.BaseMessage
import com.onion.core.platformhub.core.models.EventStreamOutput
import com.onion.core.platformhub.extra.subscribe
import com.onion.feature.home.api.HomeApiService
import com.onion.feature.home.model.HomeBannerModel
import com.onion.feature.home.model.HomeCityModel
import com.onion.feature.home.model.HomeHotModel
import com.onion.feature.home.model.HomeKingKongModel
import com.onion.feature.home.model.HomeListModel
import com.onion.feature.home.model.Mock
import com.onion.feature.home.plugin.HomePlugin
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.serialization.json.Json
import java.text.SimpleDateFormat
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: HomeRepositoryImpl
 * Author: admin by 张琦
 * Date: 2024/7/28 14:52
 * Description:
 */
@Singleton
internal class HomeRepositoryImpl @Inject constructor(
    val homeApiService: HomeApiService,
    @DefaultHttp json: Json,
    private val homePlugin: HomePlugin
): HomeRepository {

    override suspend fun getHomeBanner(): HttpState<HomeBannerModel?> {
        return HttpState.onSuccess(Mock.homeBanner())
    }

    override suspend fun getHomeKingKong(): HttpState<HomeKingKongModel?> {
        return HttpState.onSuccess(Mock.homeKingKong())
    }

    override suspend fun getHomeHot(): HttpState<HomeHotModel?> {
        return HttpState.onSuccess(Mock.homeHot())
    }

    override suspend fun getHomeList(): HttpState<HomeListModel?> {
        return HttpState.onSuccess(Mock.homeList())
    }

    override suspend fun getUserInfo(): SharedFlow<EventStreamOutput> {
        val stream = BaseMessage.EventStream("GetUserInfo","AppPlugin")
        return homePlugin.subscribe(stream)
    }

    @SuppressLint("SimpleDateFormat")
    override suspend fun getCity(): HttpState<HomeCityModel> {
        val todayDate = System.currentTimeMillis()
        val format = SimpleDateFormat("mm月dd日")
        val today = format.format(todayDate)
        return HttpState.onSuccess(HomeCityModel("上海","","Today",today))
    }
}