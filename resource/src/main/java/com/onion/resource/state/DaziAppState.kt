package com.onion.resource.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: DaziAppState
 * Author: admin by 张琦
 * Date: 2024/9/10 21:52
 * Description:
 */
val LocalDaziAppState = compositionLocalOf<DaziAppState>{
    error("No LocalNavController provided")
}

@Composable
fun ProvidableCompositionLocal<DaziAppState>.get(): DaziAppState {
    return current
}

@Composable
fun rememberDaziAppState(
    navController: NavHostController = rememberNavController(),
    showBottomBar: MutableStateFlow<Boolean> = remember { MutableStateFlow(true) }
): DaziAppState {
    return remember(navController,showBottomBar) {
        DaziAppState(navController,showBottomBar)
    }
}

@Stable
class DaziAppState(
    val navController: NavHostController,
    private val _showBottomBar: MutableStateFlow<Boolean>
) {

    lateinit var startDestination: String
    val showBottomBar: StateFlow<Boolean> = _showBottomBar.asStateFlow()

    fun hide(){
        _showBottomBar.tryEmit(false)
    }
}