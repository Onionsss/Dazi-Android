package com.onion.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.onion.feature.home.databinding.HomeFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: HomeFragment
 * Author: admin by 张琦
 * Date: 2024/7/2 23:49
 * Description:
 */
@AndroidEntryPoint
class HomeFragment: Fragment() {

    private val mBinding: HomeFragmentBinding by lazy {
        HomeFragmentBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return mBinding.root
    }

}