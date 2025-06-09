package com.onion.feature.home.ui.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.onion.feature.home.model.HomeListSingleCard
import com.onion.resource.theme.AppTheme

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: HomeSingleCardView
 * Author: admin by 张琦
 * Date: 2024/12/5 15:14
 * Description:
 */
@Composable
internal fun HomeSingleCardView(model: HomeListSingleCard) {

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Spacer(Modifier.height(12.dp))
        Text(text = model.title, color = AppTheme.colors.textPrimaryColor, fontWeight = FontWeight.W600, fontSize = 26.sp, lineHeight = 26.sp)
        Text(text = model.subTitle,color = AppTheme.colors.textSubColor, fontSize = 12.sp, fontWeight = FontWeight.W300)
        Spacer(Modifier.height(8.dp))
        HomeSingleCardViewItem(Modifier,model)
    }
}

@Composable
internal fun HomeSingleCardViewItem(modifier: Modifier,model: HomeListSingleCard){
    ConstraintLayout(
        modifier = modifier
            .aspectRatio(1 / 1.1F)
            .shadow(16.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(Color.Transparent)
    ) {
        val (border, topView, cover, introduce) = createRefs()

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

        Column(modifier = Modifier
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
            .padding(horizontal = 20.dp, vertical = 15.dp)
            .constrainAs(introduce) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }) {

//            val (title) = createRefs()
            Spacer(modifier = Modifier.weight(1F))
            Text(
                text = model.tag,
                color = Color.White.copy(0.8F),
                fontSize = 12.sp,
                lineHeight = 14.sp,
                fontWeight = FontWeight.W500,
                modifier = Modifier)
            Text(
                text = model.title,
                color = Color.White,
                fontSize = 28.sp,
                lineHeight = 30.sp,
                fontWeight = FontWeight.W600,
                modifier = Modifier)
            Text(
                text = model.subTitle,
                color = Color.White.copy(0.8F),
                fontSize = 12.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight.W500,
                modifier = Modifier)
        }

        Box(modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(16.dp))
            .border(
                2.dp, Color(model.primaryColor),
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
                .clip(RoundedCornerShape(topStart = 16.dp, bottomEnd = 8.dp))
                .background(Color(model.primaryColor))
                .padding(horizontal = 8.dp, vertical = 2.dp)
                .constrainAs(topView) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }) {
            Text(model.topTitle, color = Color.White, fontSize = 10.sp)
        }
    }
}