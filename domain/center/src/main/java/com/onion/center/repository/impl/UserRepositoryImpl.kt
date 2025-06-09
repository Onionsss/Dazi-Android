package com.onion.center.repository.impl

import com.onion.center.api.UserApiService
import com.onion.center.protocol.LoginStatus
import com.onion.center.protocol.bean.User
import com.onion.center.repository.UserRepository
import com.onion.core.common.http.ext.getSuccessData
import com.onion.core.common.http.ext.isSuccess
import com.onion.core.common.http.ext.syncCallHttpState
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: UserRepositoryImpl
 * Author: admin by 张琦
 * Date: 2024/7/4 22:49
 * Description:
 */
@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userApiService: UserApiService
): UserRepository {

    override fun loginStatus(): LoginStatus {
        return LoginStatus(true)
    }

    override suspend fun getUserInfo(): User? {
        val state = syncCallHttpState { userApiService.getUserInfo() }
        return if(state.isSuccess()) {
            state.getSuccessData()
        }else{
            null
        }
    }

}