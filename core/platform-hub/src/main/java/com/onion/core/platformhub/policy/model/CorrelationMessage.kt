package com.onion.core.platformhub.policy.model

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: CorrelationMessage
 * Author: admin by 张琦
 * Date: 2024/6/17 23:57
 * Description:
 */
data class CorrelationMessage(
    val uuid: String,
    val messageName: String,
    val pluginName: String
)