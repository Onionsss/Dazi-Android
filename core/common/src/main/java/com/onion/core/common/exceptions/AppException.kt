package com.onion.core.common.exceptions

import android.util.Log

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: AppException
 * Author: admin by 张琦
 * Date: 2024/6/29 14:00
 * Description:
 */
class AppException(e: Throwable): Exception() {

    init {
        Log.d("AppException", ": ${e.toString()}")
    }

}