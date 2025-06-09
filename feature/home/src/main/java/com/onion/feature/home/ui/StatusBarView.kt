package com.onion.feature.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.onion.resource.StatusBarHeight

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: StatusbarView
 * Author: admin by 张琦
 * Date: 2024/12/3 17:30
 * Description:
 */
@Composable
internal fun StatusBarView() {
    Box(modifier = Modifier.fillMaxWidth().height(StatusBarHeight)) {}
}