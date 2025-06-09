package com.onion.core.platformhub.handler

import com.onion.core.platformhub.core.models.BaseMessage
import org.json.JSONObject

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: IPluginQueryHandler
 * Author: admin by 张琦
 * Date: 2024/6/29 15:12
 * Description:
 */
interface IPluginQueryHandler: IPluginHandler{

    fun query(query: BaseMessage.Query): JSONObject

}