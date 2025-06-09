package com.onion.center.bean

import com.flyco.tablayout.listener.CustomTabEntity

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: TabEntity
 * Author: admin by 张琦
 * Date: 2024/7/2 23:25
 * Description:
 */
data class TabEntity(val title: String, val selectedIcon: Int = 0, val unSelectedIcon: Int = 0):
    CustomTabEntity {

    override fun getTabTitle(): String {
        return title
    }

    override fun getTabSelectedIcon(): Int {
        return selectedIcon
    }

    override fun getTabUnselectedIcon(): Int {
        return unSelectedIcon
    }
}