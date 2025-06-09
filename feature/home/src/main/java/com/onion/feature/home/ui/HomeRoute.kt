package com.onion.feature.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.onion.feature.home.model.HomeDayChooseCard
import com.onion.feature.home.model.HomeFourPublisherCard
import com.onion.feature.home.model.HomeListNew
import com.onion.feature.home.model.HomeListSingleCard
import com.onion.feature.home.model.HomeListSinglePublisher
import com.onion.feature.home.model.HomeListTwoCard
import com.onion.feature.home.model.HomePublisherScrollCard
import com.onion.feature.home.ui.item.HomeCardNewView
import com.onion.feature.home.ui.item.HomeDayChooseView
import com.onion.feature.home.ui.item.HomeFourPublisherView
import com.onion.feature.home.ui.item.HomePublisherScrollView
import com.onion.feature.home.ui.item.HomeSingleCardView
import com.onion.feature.home.ui.item.HomeSinglePublisherView
import com.onion.feature.home.ui.item.HomeTwoCardView
import com.onion.feature.home.uistate.HomeBannerUiState
import com.onion.feature.home.uistate.HomeHotUiState
import com.onion.feature.home.uistate.HomeKingKongUiState
import com.onion.feature.home.uistate.HomeListUiState
import com.onion.feature.home.uistate.UserInfoUiState
import com.onion.feature.home.viewmodel.HomeViewModel
import com.onion.resource.theme.AppTheme
import com.onion.router.Destination

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: HomeScreen
 * Author: admin by 张琦
 * Date: 2024/8/21 20:53
 * Description:
 */
fun NavGraphBuilder.homeRoute() {
    composable(Destination.Main.HOME) {
        Box {
            Text(
                text = "Home", modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
fun HomeScreenRoute() {
    HomeScreen()
}

@Composable
internal fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val bannerState by viewModel.bannerState.collectAsStateWithLifecycle()
    val kingKingState by viewModel.kingKongState.collectAsStateWithLifecycle()
    val hotState by viewModel.hotState.collectAsStateWithLifecycle()
    val listState by viewModel.listState.collectAsStateWithLifecycle()
    val userState by viewModel.userState.collectAsStateWithLifecycle()
    val cityState by viewModel.cityState.collectAsStateWithLifecycle()

    val pagerCount = rememberSaveable { Int.MAX_VALUE }
    var showAvatar by rememberSaveable {
        mutableStateOf(false)
    }

    val bannerPagerState =
        rememberPagerState(initialPage = pagerCount / 2, pageCount = { pagerCount })

    val listViewState = rememberLazyListState()

    LaunchedEffect(listState) {
        snapshotFlow { listViewState.firstVisibleItemIndex to listViewState.firstVisibleItemScrollOffset }
            .collect { (index, offset) ->
                // 每次滑动都会触发这里的回调
                showAvatar = index >= 1
            }
    }

    val loadState by remember {
        derivedStateOf {
            bannerState is HomeBannerUiState.Success
                    &&
                    kingKingState is HomeKingKongUiState.Success
                    &&
                    hotState is HomeHotUiState.Success
                    &&
                    listState is HomeListUiState.Success
        }
    }

    val avatarClick = {

    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(AppTheme.colors.pageBackground)) {

        if (loadState) {
            val listLocal = listState as HomeListUiState.Success
            val list = listLocal.model.list

            LazyColumn(modifier = Modifier
                .fillMaxSize(), state = listViewState) {

                item {
                    HomeHeaderView(modifier = Modifier, cityState,userState)
                }

                items(list.size){ index ->
                    val listModel = list[index]
                    Box(modifier = Modifier.padding(bottom = 12.dp)) {
                        when(val local = listModel){
                            is HomeListNew -> {
                                HomeCardNewView(local)
                            }
                            is HomeListSinglePublisher -> {
                                HomeSinglePublisherView(local)
                            }
                            is HomeListTwoCard -> {
                                HomeTwoCardView(local)
                            }
                            is HomeListSingleCard -> {
                                HomeSingleCardView(local)
                            }
                            is HomeFourPublisherCard -> {
                                HomeFourPublisherView(local)
                            }
                            is HomeDayChooseCard -> {
                                HomeDayChooseView(local)
                            }
                            is HomePublisherScrollCard -> {
                                HomePublisherScrollView(local)
                            }
                        }
                    }
                }
            }
        }

        StatusBarView()

        if(showAvatar && userState is UserInfoUiState.Success){
            val user = (userState as UserInfoUiState.Success).model
            RotateAvatarView(modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 60.dp, end = 20.dp),user!!,click = avatarClick)
        }
    }
}
