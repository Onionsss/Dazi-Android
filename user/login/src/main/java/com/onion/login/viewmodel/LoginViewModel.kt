package com.onion.login.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onion.core.common.http.HttpState
import com.onion.login.bean.LoginCode
import com.onion.login.bean.LoginReq
import com.onion.login.bean.LoginResp
import com.onion.login.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: LoginViewModel
 * Author: admin by 张琦
 * Date: 2024/3/24 19:14
 * Description:
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    val loginRepository: LoginRepository
): ViewModel() {

    private val _sendCodeState: MutableStateFlow<LoginCode?> = MutableStateFlow(null)
    val sendCodeState: StateFlow<LoginCode?> = _sendCodeState.asStateFlow()

    private val _loginState: MutableStateFlow<HttpState<LoginResp?>> = MutableStateFlow(HttpState.Loading())
    val loginState: StateFlow<HttpState<LoginResp?>> = _loginState.asStateFlow()

    /**
     * 发送验证码
     */
    fun sendCode(){

    }

    fun login(loginReq: LoginReq){
        viewModelScope.launch {
            _loginState.value = loginRepository.login(loginReq)
        }
    }
}