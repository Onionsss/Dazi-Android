package com.onion.core.constants.http

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: HttpCode
 * Author: admin by 张琦
 * Date: 2024/3/3 18:57
 * Description:
 */
object HttpConstant {

    const val BASEURL = "https://2451rt8126.vicp.fun/"
    const val SERVICE = "dazi-auth/"

    private val FAILED_CODE_LIST = arrayListOf(Failed.NORMAL,Failed.MESSAGE)

    fun isFailedCode(code: Int): Boolean {
        return code in FAILED_CODE_LIST
    }

    object Success{
        const val OK = 200
    }

    object Failed{
        const val NORMAL = -1
        const val MESSAGE = -2
    }
}