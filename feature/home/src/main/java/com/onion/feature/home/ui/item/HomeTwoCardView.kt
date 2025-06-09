package com.onion.feature.home.ui.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.onion.feature.home.model.HomeListTwoCard
import com.onion.feature.home.model.HomeListTwoCardItem

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: HomeTwoCardView
 * Author: admin by 张琦
 * Date: 2024/12/4 17:08
 * Description:
 */
@Composable
internal fun HomeTwoCardView(model: HomeListTwoCard) {

    Row(modifier = Modifier.padding(horizontal = 16.dp)) {
        HomeTwoCardViewItem(Modifier.weight(1F),model.list.first())
        Spacer(modifier = Modifier.width(12.dp))
        HomeTwoCardViewItem(Modifier.weight(1F),model.list.last())
    }

}

@Composable
internal fun HomeTwoCardViewItem(modifier: Modifier,model: HomeListTwoCardItem){
    ConstraintLayout(
        modifier = modifier
            .aspectRatio(1 / 1.3F)
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
            .padding(horizontal = 20.dp, vertical = 15.dp)
            .constrainAs(introduce) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }) {

            val (title) = createRefs()
            Text(
                text = model.title,
                color = Color.White,
                fontSize = 14.sp,
                lineHeight = 16.sp,
                fontWeight = FontWeight.W500,
                modifier = Modifier.constrainAs(title) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                })
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
                .clip(RoundedCornerShape(topStart = 16.dp, bottomEnd = 16.dp))
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