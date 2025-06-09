package com.onion.feature.home

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect

/**
 * Copyright (C), 2023-2025 Meta
 * FileName: LogUtil
 * Author: admin by 张琦
 * Date: 2025/1/8 18:45
 * Description:
 */
object LogUtil {

    @Composable
    fun logSide(value: String){
        SideEffect {
            Log.d("LogUtil", "logSide: ${value}")
        }
    }

    @Composable
    fun log(value: String){
        Log.d("LogUtil", "log: ${value}")
    }
}