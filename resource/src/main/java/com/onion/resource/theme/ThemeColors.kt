package com.onion.resource.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: ThemeColors
 * Author: admin by 张琦
 * Date: 2024/8/20 21:03
 * Description:
 */

data class ThemeColors(
    val textPrimaryColor: Color,
    val textSubColor: Color,
    val textShallowColor: Color,
    val primaryColor: Color,
    val pageBackground: Color,
    val primaryRed: Color,
    val white: Color,
)

val darkThemeColor = ThemeColors(
    textPrimaryColor = Color.Black,
    primaryColor = Color(0xFF409EFF),
    pageBackground = Color(0xFFF2F2F2),
    textSubColor = Color(0xFF666666),
    textShallowColor = Color(0xFF999999),
    primaryRed = Color(0xFFDB0011),
    white = Color(0xFFFFFFFF),
)

val lightThemeColor = ThemeColors(
    textPrimaryColor = Color(0xFF111111),
    primaryColor = Color(0xFF409EFF),
    pageBackground = Color(0xFFF2F2F2),
    textSubColor = Color(0xFF666666),
    textShallowColor = Color(0xFF999999),
    primaryRed = Color(0xFFDB0011),
    white = Color(0xFFFFFFFF),
)

@Deprecated("use AppTheme")
val LocalThemeColors = compositionLocalOf { lightThemeColor }

