package com.onion.feature.home.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.onion.center.protocol.bean.User
import com.onion.core.constants.http.ImageConstant

/**
 * Copyright (C), 2023-2025 Meta
 * FileName: RotateAvatarView
 * Author: admin by 张琦
 * Date: 2025/1/8 14:14
 * Description:
 */
@Composable
internal fun RotateAvatarView(modifier: Modifier,user: User,click: () -> Unit) {

    var rotationValue by rememberSaveable {
        mutableFloatStateOf(0F)
    }

    val rotation = remember { Animatable(rotationValue) }

    LaunchedEffect(Unit) {
        // 不断循环动画
        while (true) {
            rotation.animateTo(
                targetValue = 360f, // 转一圈
                animationSpec = tween(durationMillis = 50000, easing = LinearEasing) // 动画持续时间和匀速动画
            )
            // 重置角度为 0f
            rotation.snapTo(0f)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            rotationValue = rotation.value
        }
    }

    Box(modifier = modifier) {
        Box(modifier = Modifier
            .size(62.dp)
            .clip(CircleShape)
            .align(Alignment.Center)
            .background(
                Color.White
            ))

        Image(
            painter = rememberAsyncImagePainter(model = ImageConstant.link(user.avatar)),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .align(Alignment.Center)
                .clickable {
                    click()
                }
                .graphicsLayer(rotationZ = rotation.value)
        )
    }
}