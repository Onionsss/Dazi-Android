package com.onion.feature.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.onion.feature.home.model.HomeKingKongModel
import com.onion.resource.theme.AppTheme

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: HomeKingKongView
 * Author: admin by 张琦
 * Date: 2024/11/28 17:15
 * Description:
 */
@Composable
internal fun HomeKingKongView(modifier: Modifier,model: HomeKingKongModel) {

    val list = model.list

    Box(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
            list.forEachIndexed { index, homeKingKongItem ->
                val item = list[index]
                Box(modifier = Modifier.weight(1F).wrapContentHeight()) {
                    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = rememberAsyncImagePainter(model = item.imageUrl),
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(40.dp).clip(RoundedCornerShape(8.dp))
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(item.name, fontSize = 12.sp, color = AppTheme.colors.textPrimaryColor)
                    }
                }
            }
        }
    }

}