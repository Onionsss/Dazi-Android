package com.onion.center.plugin.handler

import com.onion.center.protocol.AppConstant
import com.onion.core.platformhub.core.models.BaseMessage
import com.onion.core.platformhub.extra.pluginResultSuccess
import com.onion.core.platformhub.handler.IPluginQueryHandler
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: GetNamePluginQuery
 * Author: admin by 张琦
 * Date: 2024/6/29 15:29
 * Description:
 */
@Singleton
class GetNamePluginQuery @Inject constructor(): IPluginQueryHandler {
    override fun query(query: BaseMessage.Query): JSONObject {
        return "haha".pluginResultSuccess()
    }

    override val messageName: String
        get() = "GetAppName"

    override val pluginName: String
        get() = AppConstant.Plugin.AppPlugin


}