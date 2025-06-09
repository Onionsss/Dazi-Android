package com.onion.feature.home.ui.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberAsyncImagePainter
import com.onion.feature.home.model.HomeDayChooseCard
import com.onion.feature.home.model.NftPublisher
import com.onion.resource.theme.AppTheme

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: HomePublisherScrollView
 * Author: admin by 张琦
 * Date: 2024/12/17 17:43
 * Description:
 */
@Composable
internal fun HomeDayChooseView(model: HomeDayChooseCard) {

    val publishers = model.publishers

    var itemHeight by remember {
        mutableIntStateOf(0)
    }

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Spacer(Modifier.height(12.dp))
        Text(
            text = model.topTitle,
            color = AppTheme.colors.textPrimaryColor,
            fontWeight = FontWeight.W600,
            fontSize = 26.sp,
            lineHeight = 26.sp
        )
        Spacer(Modifier.height(8.dp))

        ConstraintLayout(
            modifier = Modifier
                .aspectRatio(1 / 1.2F)
                .shadow(16.dp, RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
        ) {
            val (cover, tag, title, listView) = createRefs()

            Image(
                painter = rememberAsyncImagePainter(model = model.imageUrl),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1 / 0.5F)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .constrainAs(cover) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
            )

            Text(text = model.tag, fontSize = 12.sp,color = AppTheme.colors.primaryColor, modifier = Modifier.constrainAs(tag) {
                bottom.linkTo(title.top)
                start.linkTo(title.start)
            })

            Text(
                text = model.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.W500,
                color = Color.White,
                modifier = Modifier.constrainAs(title) {
                    bottom.linkTo(cover.bottom, 20.dp)
                    start.linkTo(cover.start, 20.dp)
                })

            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .onSizeChanged {
                    itemHeight = it.height
                }
                .constrainAs(listView) {
                    top.linkTo(cover.bottom)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                }, userScrollEnabled = false) {
                items(publishers.size) { index ->
                    val item = publishers[index]
                    PublishersItem(itemHeight, item)
                }
            }
        }
    }
}

@Composable
internal fun PublishersItem(itemHeight: Int, publisher: NftPublisher) {
    val density = LocalDensity.current
    val height = with(density) {
        (itemHeight / 3F).toDp()
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .padding(vertical = 10.dp, horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = publisher.imageUrl),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(16.dp))

        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1F).wrapContentHeight()) {
            Text(
                text = publisher.name,
                fontSize = 12.sp,
                color = AppTheme.colors.textPrimaryColor,
                fontWeight = FontWeight.W500,
                lineHeight = 12.sp
            )
            Text(
                text = publisher.description,
                fontSize = 12.sp,
                color = AppTheme.colors.textShallowColor,
                lineHeight = 12.sp
            )
        }

        Column(modifier = Modifier.wrapContentSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(modifier = Modifier
                .wrapContentSize()
                .clip(RoundedCornerShape(25.dp))
                .clickable {

                }
                .background(Color.Gray.copy(0.3F))
                .padding(vertical = 4.dp, horizontal = 22.dp)) {
                Text(
                    "关注",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W500,
                    color = AppTheme.colors.primaryColor,
                    modifier = Modifier.align(
                        Alignment.Center
                    )
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "App内购买",
                fontSize = 10.sp,
                color = AppTheme.colors.textShallowColor,
                lineHeight = 12.sp
            )
        }
    }
}