package com.onion.core.platformhub.core.models

import com.onion.core.platformhub.core.exceptions.PluginInternalError
import org.json.JSONObject

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: EventStreamOutput
 * Author: admin by 张琦
 * Date: 2024/6/23 23:29
 * Description:
 */
data class EventStreamOutput(
    private val payload: JSONObject,
    val correlationMessage: Message? = null
){

    @Suppress("DataClassShouldBeImmutable")
    var result: Result<JSONObject> = Result.success(payload)
        private set

    constructor(
        error: PluginInternalError,
        correlationMessage: Message? = null
    ): this(payload = JSONObject(),correlationMessage){
        this.result = Result.failure(error)
    }
}

data class EventStreamOutputError(
    override val errorCode: Int,
    override val message: String?,
    override val payload: JSONObject? = null
): PluginInternalError()