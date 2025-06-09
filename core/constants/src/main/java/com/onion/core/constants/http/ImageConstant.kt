package com.onion.core.constants.http

object ImageConstant {

    const val IMG_PATH: String = "https://linli-community.oss-cn-beijing.aliyuncs.com/"

    fun link(link: String?): String{
        return IMG_PATH + link
    }
}