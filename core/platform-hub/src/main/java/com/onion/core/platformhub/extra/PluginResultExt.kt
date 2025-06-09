package com.onion.core.platformhub.extra

import com.onion.core.common.http.BaseResult
import com.onion.core.platformhub.core.util.json
import com.onion.core.platformhub.core.util.objectToJson
import kotlinx.serialization.encodeToString
import org.json.JSONObject

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: PluginResultExt
 * Author: admin by 张琦
 * Date: 2024/6/29 15:05
 * Description:
 */
inline fun <reified T> T.pluginResultSuccess(): JSONObject {
    return pluginResult()
}

/**
 * 不需要baseResult
 */
inline fun <reified T> T.pluginSuccess(): JSONObject {
    return try {
        JSONObject(
            json.encodeToString(this)
        )
    } catch (e: Exception) {
        JSONObject()
    }
}

inline fun <reified T> T.pluginResultTransfer(): JSONObject {
    return this?.pluginResult() ?: pluginResultFailed()
}

inline fun <reified T> T.pluginResultFailed(): JSONObject {
    return pluginResult(code = -1)
}

inline fun <reified T> T.pluginResult(
    code: Int = 200,
    message: String = ""
): JSONObject {
    return JSONObject(
        objectToJson(
            BaseResult(
                code,message,this
            )
        )
    )
}