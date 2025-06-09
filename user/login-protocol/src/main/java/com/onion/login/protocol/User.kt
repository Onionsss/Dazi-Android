package com.onion.login.protocol

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Copyright (C), 2023-2023 Meta
 * FileName: User
 * Author: admin by 张琦
 * Date: 2023/4/10 23:47
 * Description:
 */
@Parcelize
data class User(
    var phone: String? = null,//手机号
    var token: String? = null,//token
    var password: String? = null,//密码
): Parcelable {

}