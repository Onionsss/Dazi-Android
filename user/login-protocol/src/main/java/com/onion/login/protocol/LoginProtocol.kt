package com.onion.login.protocol

import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Copyright (C), 2023-2023 Meta
 * FileName: LoginProtocol
 * Author: admin by 张琦
 * Date: 2023/4/10 23:01
 * Description:
 */
interface LoginProtocol {

    fun isLogin(): Boolean

    fun getUserInfo(): User

    fun setLoginInfo(user: User)

    fun loginFlow(loginOrExit: Boolean)

    fun getLoginFlow(): MutableStateFlow<Boolean>

}