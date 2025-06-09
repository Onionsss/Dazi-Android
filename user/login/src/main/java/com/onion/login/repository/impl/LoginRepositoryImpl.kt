package com.onion.login.repository.impl

import com.onion.core.common.di.DaziCoroutineScope
import com.onion.core.common.di.IoDispatcher
import com.onion.core.common.http.HttpState
import com.onion.core.common.http.ext.syncCallHttpState
import com.onion.login.api.LoginApiService
import com.onion.login.bean.LoginReq
import com.onion.login.bean.LoginResp
import com.onion.login.repository.LoginRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: LoginRepositoryImpl
 * Author: admin by 张琦
 * Date: 2024/6/29 13:41
 * Description:
 */
@Singleton
class LoginRepositoryImpl @Inject constructor(
    @IoDispatcher val dispatcher: CoroutineDispatcher,
    val api: LoginApiService
): LoginRepository {

    override suspend fun login(loginReq: LoginReq): HttpState<LoginResp?> {
        val state = syncCallHttpState(dispatcher) {
            api.login()
        }

        return state
    }


}