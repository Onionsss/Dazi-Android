package com.onion.feature.home.ui.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberAsyncImagePainter
import com.onion.feature.home.model.HomeFourPublisherCard

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: HomeFourPublisherView
 * Author: admin by 张琦
 * Date: 2024/12/17 16:08
 * Description:
 */
@Composable
internal fun HomeFourPublisherView(model: HomeFourPublisherCard) {

    val publisherList = model.publishers
    ConstraintLayout(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .shadow(16.dp, RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .aspectRatio(1 / 1.2F)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.Transparent)
    ) {
        val (cover, publishers) = createRefs()

        Image(
            painter = rememberAsyncImagePainter(model = model.imageUrl),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .constrainAs(cover) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .clip(RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp))
                .constrainAs(publishers) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }) {
            Image(
                painter = rememberAsyncImagePainter(model = model.imageUrl),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                    .blur(15.dp)
            )

            Row(modifier = Modifier.fillMaxSize()) {
                publisherList.forEach { item ->
                    Box(modifier = Modifier
                        .weight(1F)
                        .fillMaxHeight()) {

                        Image(
                            painter = rememberAsyncImagePainter(model = item.imageUrl),
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(55.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .align(Alignment.Center)
                        )
                    }

                }
            }

        }
    }
}