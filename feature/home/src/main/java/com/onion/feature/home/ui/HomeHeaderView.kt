package com.onion.feature.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.onion.center.protocol.bean.User
import com.onion.core.constants.http.ImageConstant
import com.onion.feature.home.model.Mock
import com.onion.feature.home.uistate.HomeCityUiState
import com.onion.feature.home.uistate.UserInfoUiState
import com.onion.resource.StatusBarHeight
import com.onion.resource.theme.AppTheme

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: HomeHeaderView
 * Author: admin by 张琦
 * Date: 2024/11/28 19:28
 * Description:
 */
@Composable
internal fun HomeHeaderView(modifier: Modifier, cityState: HomeCityUiState, userState: UserInfoUiState) {

    val user = if(userState is UserInfoUiState.Success){
        userState.model
    }else{
        User()
    }

    val city = if(cityState is HomeCityUiState.Success){
        cityState.model
    }else{
        Mock.homeCity()
    }

    Box(modifier = modifier
        .wrapContentSize()
        .padding(horizontal = 16.dp, vertical = 8.dp)) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.height(StatusBarHeight)) { }
            Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.Bottom) {
                Text(city?.today ?: "", fontSize = 28.sp, fontWeight = FontWeight.W600,color = AppTheme.colors.textPrimaryColor)
                Spacer(modifier = Modifier.width(10.dp))
                Text(city?.todayDate ?: "", fontSize = 18.sp, fontWeight = FontWeight.W500,color = AppTheme.colors.textShallowColor)
                Spacer(modifier = Modifier.weight(1F))
                Box(modifier = Modifier) {
                    Box(modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .align(Alignment.Center)
                        .background(
                            Color.White
                        ))
                    Image(
                        painter = rememberAsyncImagePainter(model = ImageConstant.link(user?.avatar)),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .align(Alignment.Center)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = user?.nickname ?: "",color = AppTheme.colors.textPrimaryColor, fontSize = 14.sp, fontWeight = FontWeight.W500)
            }
        }
    }
}