package com.onion.login.widget

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.onion.resource.R as Resource

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: LoginWidget
 * Author: admin by 张琦
 * Date: 2024/5/25 19:09
 * Description:
 */

/**
 * 更多登录方式
 */
@Composable
internal fun moreLogin(modifier: Modifier,list: ArrayList<LoginIcon>){
    Row(modifier = modifier) {
        list.forEach {
            loginIcon(it)
        }
    }
}

@Composable
internal fun loginIcon(icon: LoginIcon){
    IconButton(modifier = Modifier
        .padding(start = 10.dp, end = 10.dp)
        .size(35.dp)
        .clip(CircleShape)
        .border(
            1.dp,
            colorResource(id = Resource.color.resource_line),
            CircleShape
        )
        .padding(8.dp),
        onClick = { icon.click(icon.id) }) {
        Icon(modifier = Modifier.fillMaxSize(), imageVector = ImageVector.vectorResource(icon.icon), contentDescription = icon.tag,tint = Color.Unspecified)
    }
}

@Stable
data class LoginIcon(
    val id: Int,
    val icon: Int,
    val tag: String,
    val click: (Int) -> Unit
){}

@Preview
@Composable
internal fun loginIconPreview(){
    loginIcon(LoginIcon(1,1,"string",{}))
}