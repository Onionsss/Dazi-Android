package com.onion.feature.my

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.onion.resource.theme.AppTheme
import com.onion.router.Destination

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: HomeRoute
 * Author: admin by 张琦
 * Date: 2024/9/15 13:21
 * Description:
 */
fun NavGraphBuilder.myRoute() {
    composable(Destination.Main.MY) {
        MyScreen()
    }
}

@Composable
fun MyScreen() {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (background, setting) = createRefs()

        /**
         * 顶部背景
         */
        ConstraintLayout(modifier = Modifier.fillMaxWidth().aspectRatio(1/1.2F).background(Color.Black)) {
            val (A, B) = createRefs()
            // B 布局
            Box(
                modifier = Modifier.fillMaxWidth().wrapContentHeight().background(Color.Blue).constrainAs(B) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(A.top)
                }
            ) {
                // B 的内容
                Text("123123123")
            }
            // A 布局
            Box(
                modifier = Modifier.fillMaxWidth().wrapContentHeight().background(Color.Red).constrainAs(A) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            ) {
                // A 的内容
                Text("123")
            }


        }


    }
}

@Composable
fun background() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(150.dp)
            .background(AppTheme.colors.primaryColor)
    ) {

    }
}