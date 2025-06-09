package com.onion.feature.home.ui.item

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberAsyncImagePainter
import com.onion.feature.home.model.HomeListSinglePublisher
import com.onion.resource.theme.AppTheme

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: HomeSinglePublisherView
 * Author: admin by 张琦
 * Date: 2024/12/4 14:53
 * Description:
 */
@Composable
internal fun HomeSinglePublisherView(model: HomeListSinglePublisher) {
    val publisher = model.publisher

    val shape = remember { RoundedCornerShape(16.dp) }
    val interactionSource = remember { MutableInteractionSource() }
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val alpha by infiniteTransition.animateFloat(
        1f, 0.3f, infiniteRepeatable(
            tween(2000, 0, easing = FastOutLinearInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val radius by infiniteTransition.animateFloat(
        350f, 100f, infiniteRepeatable(
            tween(2000, 0, easing = FastOutLinearInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    ConstraintLayout(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .shadow(16.dp, RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .aspectRatio(1 / 0.5F)
            .clip(RoundedCornerShape(16.dp))
//            .hoverable(interactionSource)
//            .border(
//                3.dp, Brush.radialGradient(
//                    listOf(
//                        Color.White.copy(alpha),
//                        Color.DarkGray,
//                    ),
//                    center = Offset.Zero,
//                    radius = radius
//                ), shape
//            )
            .background(Color.Transparent)
    ) {
        val (cover, avatar, tag, title, subTitle) = createRefs()

        Image(
            painter = rememberAsyncImagePainter(model = model.imageUrl),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
                .blur(5.dp)
                .constrainAs(cover) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
        )

        Image(
            painter = rememberAsyncImagePainter(model = model.publisher.imageUrl),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .shadow(16.dp, RoundedCornerShape(16.dp))
                .size(95.dp)
                .clip(RoundedCornerShape(16.dp))
                .constrainAs(avatar) {
                    top.linkTo(parent.top, 12.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Text(
            text = model.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 12.sp,
            fontWeight = FontWeight.W500,
            color = Color.White,
            modifier = Modifier.constrainAs(title) {
                top.linkTo(avatar.bottom, 8.dp)
                start.linkTo(parent.start, 20.dp)
                end.linkTo(avatar.end, (-12).dp)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            })

        Text(
            text = model.tag,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 10.sp,
            lineHeight = 10.sp,
            fontWeight = FontWeight.W500,
            color = Color.White,
            modifier = Modifier.wrapContentSize().clip(RoundedCornerShape(6.dp)).background(AppTheme.colors.primaryColor).padding(horizontal = 4.dp,2.dp).constrainAs(tag) {
                top.linkTo(title.bottom, 2.dp)
                start.linkTo(parent.start, 20.dp)
            })

        Text(
            text = model.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 12.sp,
            color = Color.White.copy(0.5F),
            modifier = Modifier.wrapContentSize().constrainAs(subTitle) {
                baseline.linkTo(tag.baseline)
                start.linkTo(tag.end, 8.dp)
                end.linkTo(avatar.end, (-12).dp)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            })
    }
}