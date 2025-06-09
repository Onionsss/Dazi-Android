package com.onion.center.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import com.onion.resource.state.LocalDaziAppState
import com.onion.resource.state.rememberDaziAppState
import com.onion.router.Destination

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: CenterScreen
 * Author: admin by 张琦
 * Date: 2024/8/20 21:51
 * Description:
 */

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun MainPage(){

    val appState = rememberDaziAppState()

    appState.startDestination = Destination.Main.MAIN
    appState.navController.addOnDestinationChangedListener { controller, destination, arguments ->

    }

    CompositionLocalProvider(LocalDaziAppState provides appState){
        Row(modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)) {
            NavHost(navController = appState.navController,
                startDestination = appState.startDestination,
                modifier = Modifier
                    .fillMaxSize()){

                mainRoute()

                RouteList.forEach { route ->
                    route()
                }
            }
        }

    }

}