package com.onion.center.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.onion.feature.home.ui.HomeScreenRoute
import com.onion.feature.my.MyScreen
import com.onion.feature.my.ui.MySideBar
import com.onion.resource.Pager
import com.onion.router.Destination

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: MainRoute
 * Author: admin by 张琦
 * Date: 2024/10/31 22:44
 * Description:
 */

fun NavGraphBuilder.mainRoute() {
    composable(Destination.Main.MAIN) {
        MainScreen()
    }
}

@Composable
fun MainScreen() {
    var selectedIndex by remember { mutableIntStateOf(0) }
    val size = items.size
    val viewPagerState = rememberPagerState { size }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val itemChecker = { index: Int ->
        selectedIndex = index
    }

    LaunchedEffect(selectedIndex) {
        viewPagerState.scrollToPage(selectedIndex)
    }

    ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
        if (drawerState.isOpen) {
            MySideBar()
        }
    }) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F)
            ) {
                MainViewPager(viewPagerState)
            }
//        BottomAppBar(selectedIndex,itemChecker)
        }
    }

}

internal val pagers = arrayListOf<Pager>({ HomeScreenRoute() },
    { HomeScreenRoute() },
    { HomeScreenRoute() },
    { MyScreen() })

@Composable
internal fun MainViewPager(state: PagerState) {
    HorizontalPager(state = state, modifier = Modifier, userScrollEnabled = false) { pager ->
        pagers[pager]()
    }
}