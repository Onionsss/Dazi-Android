package com.onion.center.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.onion.center.databinding.CenterDashboardActivityBinding
import com.onion.center.protocol.BaseConfig
import com.onion.core.common.adapter.ViewPager2Adapter
import com.onion.feature.home.HomeFragment
import com.onion.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: DashBoardActivity
 * Author: admin by 张琦
 * Date: 2024/5/20 22:07
 * Description:
 */
@AndroidEntryPoint
class DashBoardActivity: AppCompatActivity() {

    private val mBinding: CenterDashboardActivityBinding by lazy {
        CenterDashboardActivityBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var mBaseConfig: BaseConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        enableEdgeToEdge()
        initView()

        startActivity(Intent(this,LoginActivity::class.java))
    }

    private fun initView(){
        mBinding.apply {
            dashBoardVp.offscreenPageLimit = 4
            dashBoardVp.isUserInputEnabled = false
            dashBoardVp.adapter = ViewPager2Adapter(this@DashBoardActivity, arrayListOf(
                HomeFragment(),HomeFragment(),HomeFragment(),HomeFragment()
            ))
        }
    }

}