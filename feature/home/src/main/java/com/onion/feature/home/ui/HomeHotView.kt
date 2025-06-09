package com.onion.feature.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.onion.feature.home.R
import com.onion.feature.home.model.HomeHotModel
import com.onion.resource.theme.AppTheme

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: HomeHotView
 * Author: admin by 张琦
 * Date: 2024/11/28 20:20
 * Description:
 */
@Composable
internal fun HomeHotView(modifier: Modifier, pagerState: LazyListState, model: HomeHotModel) {

    val list = model.hotList

    LazyRow(modifier = modifier, state = pagerState, contentPadding = PaddingValues(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically) {

        item {
            Image(
                painter = painterResource(R.drawable.icon_hot),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(20.dp)
            )
        }

        items(list.size) { index ->
            val item = list[index]

            Row(
                modifier = Modifier
                    .wrapContentSize()
                    .clip(RoundedCornerShape(5.dp))
                    .background(AppTheme.colors.pageBackground)
                    .padding(start = 2.dp, top = 2.dp, bottom = 2.dp, end = 8.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = item.imageUrl),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(20.dp).clip(RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.width(5.dp))
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(text = item.name, fontSize = 14.sp,color = AppTheme.colors.textPrimaryColor,modifier = Modifier.align(Alignment.Center))
                }

            }
        }

    }

}