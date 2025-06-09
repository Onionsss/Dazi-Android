package com.onion.feature.my.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

/**
 * Copyright (C), 2023-2025 Meta
 * FileName: MySideBar
 * Author: admin by 张琦
 * Date: 2025/1/15 17:22
 * Description:
 */
@Composable
fun MySideBar() {
    val configuration = LocalConfiguration.current
    val width = configuration.screenWidthDp
    Box(modifier = Modifier
        .width((width * 0.7F).dp)
        .fillMaxHeight()
        .background(Color.White)) {
        Text("123")
    }
}