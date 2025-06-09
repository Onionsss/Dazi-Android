package com.onion.core.common.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: ViewPager2Adapter
 * Author: admin by 张琦
 * Date: 2024/7/2 23:07
 * Description:
 */
class ViewPager2Adapter(activity: FragmentActivity,val fragmentList: ArrayList<Fragment>): FragmentStateAdapter(activity) {
    override fun getItemCount() = fragmentList.size

    override fun createFragment(position: Int) = fragmentList[position]
}