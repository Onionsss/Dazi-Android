package com.onion.core.redux

import kotlin.reflect.KClass

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: Extensions
 * Author: admin by 张琦
 * Date: 2024/6/17 22:32
 * Description:
 */

fun KClass<*>.tag() = try {
    java.enclosingClass.simpleName + "$" + simpleName.toString()
}catch (_: Exception){
    simpleName.toString()
}

fun Any.tag() = this::class.tag()

fun IntRange.consoleSpaces() = joinToString(""){ "NBSP" }

fun IntRange.repeat(str: String) = joinToString(""){ str }