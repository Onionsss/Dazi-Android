package com.onion.feature.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onion.center.protocol.bean.User
import com.onion.core.common.http.BaseResult
import com.onion.core.common.http.ext.getSuccessData
import com.onion.core.platformhub.extra.toBean
import com.onion.feature.home.repository.HomeRepository
import com.onion.feature.home.uistate.HomeBannerUiState
import com.onion.feature.home.uistate.HomeCityUiState
import com.onion.feature.home.uistate.HomeHotUiState
import com.onion.feature.home.uistate.HomeKingKongUiState
import com.onion.feature.home.uistate.HomeListUiState
import com.onion.feature.home.uistate.UserInfoUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: HomeViewModel
 * Author: admin by 张琦
 * Date: 2024/7/28 14:51
 * Description:
 */
@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
): ViewModel() {

    private val _bannerState: MutableStateFlow<HomeBannerUiState> = MutableStateFlow(HomeBannerUiState.Loading)
    val bannerState: StateFlow<HomeBannerUiState> = _bannerState.asStateFlow()

    private val _kingKongState: MutableStateFlow<HomeKingKongUiState> = MutableStateFlow(HomeKingKongUiState.Loading)
    val kingKongState: StateFlow<HomeKingKongUiState> = _kingKongState.asStateFlow()

    /**
     * 热门
     */
    private val _hotState: MutableStateFlow<HomeHotUiState> = MutableStateFlow(HomeHotUiState.Loading)
    val hotState: StateFlow<HomeHotUiState> = _hotState.asStateFlow()

    /**
     * 列表
     */
    private val _listState: MutableStateFlow<HomeListUiState> = MutableStateFlow(HomeListUiState.Loading)
    val listState: StateFlow<HomeListUiState> = _listState.asStateFlow()

    /**
     * 用户数据
     */
    private val _userState: MutableStateFlow<UserInfoUiState> = MutableStateFlow(UserInfoUiState.Loading)
    val userState: StateFlow<UserInfoUiState> = _userState.asStateFlow()

    /**
     * 用户数据
     */
    private val _cityState: MutableStateFlow<HomeCityUiState> = MutableStateFlow(HomeCityUiState.Loading)
    val cityState: StateFlow<HomeCityUiState> = _cityState.asStateFlow()

    init {
        fetchBanner()
        fetchKingKong()
        fetchHomeHot()
        fetchHomeList()
        getUserInfo()
        fetchCity()
    }

    fun getUserInfo(){
        viewModelScope.launch {
            homeRepository.getUserInfo().collectLatest {
                val result = it.toBean<BaseResult<User>>()
                if(result.success()){
                    _userState.emit(UserInfoUiState.Success(result.data))
                }
            }
        }
    }

    /**
     * 城市和今日信息
     */
    fun fetchCity(){
        viewModelScope.launch {
            val state = homeRepository.getCity()
            state.getSuccessData().let {
                _cityState.emit(HomeCityUiState.Success(it))
            }
        }
    }

    fun fetchBanner(){
        viewModelScope.launch {
            val state = homeRepository.getHomeBanner()
            state.getSuccessData()?.let {
                _bannerState.emit(HomeBannerUiState.Success(it))
            }
        }
    }

    fun fetchKingKong(){
        viewModelScope.launch {
            val state = homeRepository.getHomeKingKong()
            state.getSuccessData()?.let {
                _kingKongState.emit(HomeKingKongUiState.Success(it))
            }
        }
    }

    fun fetchHomeHot(){
        viewModelScope.launch {
            val state = homeRepository.getHomeHot()
            state.getSuccessData()?.let {
                _hotState.emit(HomeHotUiState.Success(it))
            }
        }
    }

    fun fetchHomeList(){
        viewModelScope.launch {
            val state = homeRepository.getHomeList()
            state.getSuccessData()?.let {
                _listState.emit(HomeListUiState.Success(it))
            }
        }
    }
}