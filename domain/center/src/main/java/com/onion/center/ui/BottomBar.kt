package com.onion.center.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.MergeType
import androidx.compose.material.icons.automirrored.rounded.Message
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.bottombar.AnimatedBottomBar
import com.example.bottombar.components.BottomBarItem
import com.example.bottombar.model.IndicatorStyle
import com.onion.resource.theme.AppTheme
import com.onion.router.Destination

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: BottomBar
 * Author: admin by 张琦
 * Date: 2024/9/11 21:58
 * Description:
 */

internal val items: ArrayList<NavBottomItem> = arrayListOf(
    NavBottomItem.Home,
    NavBottomItem.Market,
    NavBottomItem.Message,
    NavBottomItem.My,
)

@Composable
internal fun BottomAppBar(selectedItem: Int,itemChecker: (Int) -> Unit){
    AnimatedBottomBar(
        selectedItem = selectedItem,
        itemSize = items.size,
        containerColor = AppTheme.colors.textPrimaryColor,
        indicatorStyle = IndicatorStyle.NONE
    ) {
        items.forEachIndexed { index, navigationItem ->
            val isSelected = selectedItem == index
            BottomBarItem(
                selected = isSelected,
                onClick = {
                    itemChecker(index)
                },
                imageVector = navigationItem.icon,
                label = navigationItem.title,
                containerColor = Color.Gray,
                textColor = AppTheme.colors.primaryColor,
                iconColor = AppTheme.colors.primaryColor
            )
        }
    }
}

internal sealed class NavBottomItem(var title: String,var route: String,var icon: ImageVector){
    data object Home: NavBottomItem("首页", Destination.Main.HOME,Icons.Rounded.Home)
    data object Market: NavBottomItem("市场", Destination.Main.MARKET,Icons.Rounded.ShoppingCart)
    data object Message: NavBottomItem("消息", Destination.Main.MESSAGE,Icons.AutoMirrored.Rounded.Message)
    data object My: NavBottomItem("我的", Destination.Main.MESSAGE,Icons.AutoMirrored.Rounded.MergeType)
}