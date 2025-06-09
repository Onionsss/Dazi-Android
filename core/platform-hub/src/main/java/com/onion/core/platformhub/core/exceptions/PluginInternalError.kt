package com.onion.core.platformhub.core.exceptions

import org.json.JSONObject

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: CyclicalPluginDependencyException
 * Author: admin by 张琦
 * Date: 2024/6/17 23:41
 * Description:
 */
abstract class PluginInternalError: RuntimeException(){

    abstract val errorCode: Int

    abstract val payload: JSONObject?

}