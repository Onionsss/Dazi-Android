package com.onion.center.repository

import com.onion.center.protocol.LoginStatus
import com.onion.center.protocol.bean.User

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: UserRepository
 * Author: admin by 张琦
 * Date: 2024/7/4 22:45
 * Description:
 */
interface UserRepository {

    fun loginStatus(): LoginStatus

    suspend fun getUserInfo(): User?
}