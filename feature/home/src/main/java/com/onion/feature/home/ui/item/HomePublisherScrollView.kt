package com.onion.feature.home.ui.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberAsyncImagePainter
import com.onion.feature.home.model.HomePublisherScrollCard
import com.onion.feature.home.model.HomeScrollMix
import kotlinx.coroutines.delay

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: HomePublisherScrollView
 * Author: admin by 张琦
 * Date: 2024/12/17 17:43
 * Description:
 */
@Composable
internal fun HomePublisherScrollView(model: HomePublisherScrollCard) {

    val list = model.publishers
    list.chunked(2)

    val row1State = rememberLazyListState()
    val row2State = rememberLazyListState()

    var distance by rememberSaveable {
        mutableFloatStateOf(0F)
    }

    val fixed by rememberSaveable {
        mutableIntStateOf(80.dp.value.toInt())
    }

    LaunchedEffect(Unit) {
        row1State.scrollToItem(Int.MAX_VALUE / 2)
        row2State.scrollToItem(Int.MAX_VALUE / 3,fixed)

        row2State.scrollBy(distance)
        row1State.scrollBy(distance)
        while (true){
            row1State.scrollBy(1F)
            row2State.scrollBy(1F)
            distance += 1F
            delay(50)
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .shadow(16.dp, RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .aspectRatio(1 / 1.1F)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.Transparent)
    ) {
        val (cover, tag, title,scroll) = createRefs()

        Image(
            painter = rememberAsyncImagePainter(model = model.imageUrl),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
                .blur(20.dp)
                .constrainAs(cover) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
        )

        Text(text = model.tag, fontSize = 12.sp,color = Color.White,modifier = Modifier.constrainAs(tag){
            bottom.linkTo(title.top)
            start.linkTo(title.start)
        })

        Text(text = model.title, fontSize = 25.sp,color = Color.White, fontWeight = FontWeight.W600,modifier = Modifier.constrainAs(title){
            start.linkTo(parent.start,20.dp)
            bottom.linkTo(parent.bottom,20.dp)
        })

        Column(modifier = Modifier.constrainAs(scroll){
            top.linkTo(parent.top)
            bottom.linkTo(tag.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }) {
            RowList(modifier = Modifier,row1State,list)
            Spacer(modifier = Modifier.height(12.dp))
            RowList(modifier = Modifier,row2State,list)
        }

    }
}

@Composable
internal fun RowList(modifier: Modifier, rowState: LazyListState, list: ArrayList<HomeScrollMix>) {
    LazyRow(modifier = modifier, state = rowState, contentPadding = PaddingValues(horizontal = 12.dp), horizontalArrangement = Arrangement.spacedBy(12.dp), userScrollEnabled = false) {
        items(Int.MAX_VALUE){ index ->
            val item = list[index % list.size]
            Image(
                painter = rememberAsyncImagePainter(model = item.nftPublisher?.imageUrl),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
        }
    }
}