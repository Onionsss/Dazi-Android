package com.onion.feature.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.onion.feature.home.model.HomeBannerModel
import kotlinx.coroutines.delay

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: HomeBannerView
 * Author: admin by 张琦
 * Date: 2024/11/28 11:47
 * Description:
 */
@Composable
internal fun HomeBannerView(modifier: Modifier, pageState: PagerState, model: HomeBannerModel) {
    val list = model.list

    val isDragged by pageState.interactionSource.collectIsDraggedAsState()

    if (!isDragged) {
        with(pageState) {
            var currentKey by rememberSaveable {
                mutableIntStateOf(0)
            }
            LaunchedEffect(currentKey) {
                delay(5000)
                val nextPager = (currentKey + 1).mod(pageCount)
                pageState.animateScrollToPage(nextPager)
                currentKey = nextPager
            }
        }
    }

    Box(modifier = modifier) {
        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = pageState,
            beyondViewportPageCount = list.size / 2
        ) { pager ->
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = rememberAsyncImagePainter(model = list[pager % list.size].imageUrl),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        HomeBannerIndicator(
            list.size, pageState.currentPage % list.size, modifier = Modifier
                .align(
                    Alignment.BottomCenter
                )
                .wrapContentSize()
                .padding(5.dp)
        )
    }

}

@Composable
fun HomeBannerIndicator(size: Int, current: Int, modifier: Modifier) {
    Row(modifier) {
        repeat(size) { index ->
            val color = if (current == index) Color.White else Color.Gray
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(6.dp)
            )
        }
    }
}
