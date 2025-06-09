package com.onion.core.common.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: ViewPager2ChildAdapter
 * Author: admin by 张琦
 * Date: 2024/7/2 23:08
 * Description:
 */
class ViewPager2ChildAdapter(fragment: Fragment,val fragmentList: ArrayList<Fragment>): FragmentStateAdapter(fragment.childFragmentManager,fragment.lifecycle) {

    override fun getItemCount() = fragmentList.size

    override fun createFragment(position: Int) = fragmentList[position]
}