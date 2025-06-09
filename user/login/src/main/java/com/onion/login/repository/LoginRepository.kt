package com.onion.login.repository

import com.onion.core.common.http.HttpState
import com.onion.login.bean.LoginReq
import com.onion.login.bean.LoginResp

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: LoginRepository
 * Author: admin by 张琦
 * Date: 2024/6/29 13:41
 * Description:
 */
interface LoginRepository {

    suspend fun login(loginReq: LoginReq): HttpState<LoginResp?>

}