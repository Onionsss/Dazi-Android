package com.onion.core.platformhub.core.util

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.InputStream

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: KotlinSerializerOperations
 * Author: admin by 张琦
 * Date: 2024/6/23 22:37
 * Description:
 */
val json = Json {
    ignoreUnknownKeys = true
    encodeDefaults = true
    isLenient = true
}

inline fun <reified T: Any> objectFromJson(payload: String): T =
    json.decodeFromString<T>(payload)

inline fun <reified T: Any> objectFromJson(payload: InputStream): T {
    val jsonString = payload.bufferedReader().use { it.readText() }
    return json.decodeFromString(jsonString)
}

inline fun <reified T: Any> objectToJson(value: T): String = json.encodeToString(value)
