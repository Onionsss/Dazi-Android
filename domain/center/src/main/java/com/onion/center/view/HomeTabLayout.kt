package com.onion.center.view

import android.content.Context
import android.util.AttributeSet
import com.flyco.tablayout.CommonTabLayout
import com.flyco.tablayout.listener.CustomTabEntity
import com.onion.center.R
import com.onion.center.bean.TabEntity

/**
 * Copyright (C), 2020-2021, 游加科技
 * FileName: HomeTabLayout
 * Author: EDZ by 张琦
 * Date: 2020/9/7 9:12C
 * Description: 底部导航
 */
class HomeTabLayout(ctx: Context,attrs: AttributeSet): CommonTabLayout(ctx,attrs) {

    private val mTabNames by lazy {
        mutableListOf("首页","社区","消息","我的")
    }

    private val mTabIconPress = mutableListOf(
        R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_foreground)

    private val mTabIconNormal = mutableListOf(
        R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_foreground)

    init {
        val tabData = mTabNames.mapIndexed{
                index, s ->  TabEntity(s,mTabIconPress[index],mTabIconNormal[index])
        }.toMutableList() as ArrayList<CustomTabEntity>

        this.setTabData(tabData)
    }

}