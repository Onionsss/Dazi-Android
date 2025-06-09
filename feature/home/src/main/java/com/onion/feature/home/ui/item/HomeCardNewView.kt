package com.onion.feature.home.ui.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberAsyncImagePainter
import com.onion.feature.home.model.HomeListNew

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: HomeCardNewView
 * Author: admin by 张琦
 * Date: 2024/12/3 18:13
 * Description:
 */
@Composable
internal fun HomeCardNewView(model: HomeListNew) {
    val publisher = model.publisher

    ConstraintLayout(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .shadow(16.dp, RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .aspectRatio(1 / 1.2F)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.Transparent)
    ) {
        val (border, topView, bottomView, cover, introduce) = createRefs()

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
                    bottom.linkTo(bottomView.top)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
        )
        ConstraintLayout(modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1 / 0.5F)
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color.Transparent,
                        Color(model.primarySubColor).copy(0.95F),
                        Color(model.primarySubColor),
                    )
                )
            )
            .padding(horizontal = 20.dp)
            .constrainAs(introduce) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(bottomView.top)
            }) {

            val (topTitle, title, subTitle) = createRefs()

            Text(
                model.tag,
                color = Color.White.copy(0.95F),
                fontSize = 12.sp,
                modifier = Modifier.constrainAs(topTitle) {
                    bottom.linkTo(title.top)
                    start.linkTo(subTitle.start)
                })
            Text(
                text = model.title,
                color = Color.White,
                fontSize = 26.sp,
                lineHeight = 32.sp,
                fontWeight = FontWeight.W500,
                modifier = Modifier.constrainAs(title) {
                    bottom.linkTo(subTitle.top)
                    start.linkTo(subTitle.start)
                })
            Text(
                model.subTitle,
                color = Color.White.copy(0.8F),
                fontSize = 12.sp,
                modifier = Modifier
                    .wrapContentSize()
                    .constrainAs(subTitle) {
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    })
        }

        Box(modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(16.dp))
            .border(
                3.dp, Color(model.primaryColor),
                RoundedCornerShape(16.dp)
            )
            .constrainAs(border) {
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }) {
        }

        Box(
            modifier = Modifier
                .wrapContentSize()
                .clip(RoundedCornerShape(topStart = 16.dp, bottomEnd = 16.dp))
                .background(Color(model.primaryColor))
                .padding(horizontal = 10.dp, vertical = 4.dp)
                .constrainAs(topView) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }) {
            Text(model.topTitle, color = Color.White, fontSize = 12.sp)
        }

        Row(
            modifier = Modifier
                .background(Color(model.primaryColor))
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .constrainAs(bottomView) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }, verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = rememberAsyncImagePainter(model = model.publisher.imageUrl),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier
                    .weight(1F)
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = publisher.name,
                    fontSize = 12.sp,
                    color = Color.White,
                    fontWeight = FontWeight.W500
                )
                Text(text = publisher.description, fontSize = 12.sp, color = Color.White.copy(0.5F))
            }

            Box(modifier = Modifier
                .wrapContentSize()
                .clip(RoundedCornerShape(25.dp))
                .clickable { }
                .background(Color.White.copy(0.3F))
                .padding(vertical = 4.dp, horizontal = 22.dp)) {
                Text(
                    "关注",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W500,
                    color = Color.White,
                    modifier = Modifier.align(
                        Alignment.Center
                    )
                )
            }
        }
    }
}

private val primaryColor = Color(0xFF24306C)
private val primaryGColor = Color(0xFF243676)