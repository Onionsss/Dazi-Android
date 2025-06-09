package com.onion.center.plugin.handler

import com.onion.center.protocol.AppConstant
import com.onion.center.repository.UserRepository
import com.onion.core.platformhub.core.models.BaseMessage
import com.onion.core.platformhub.extra.pluginResultSuccess
import com.onion.core.platformhub.handler.IPluginQueryHandler
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: GetLoginStatusQuery
 * Author: admin by 张琦
 * Date: 2024/7/4 22:43
 * Description:
 */
@Singleton
class GetLoginStatusQuery @Inject constructor(
    private val userRepository: UserRepository
): IPluginQueryHandler {
    override fun query(query: BaseMessage.Query): JSONObject {
        return userRepository.loginStatus().pluginResultSuccess()
    }

    override val messageName: String
        get() = "GetLoginStatus"

    override val pluginName: String
        get() = AppConstant.Plugin.AppPlugin


}