package com.onion.resource.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: AppTheme
 * Author: admin by 张琦
 * Date: 2024/8/20 21:06
 * Description:
 */

object AppTheme{

    private val colorState = MutableStateFlow(0)

    fun dark(){
        colorState.value = 1
    }

    fun light(){
        colorState.value = 0
    }

    @Composable
    fun getColorState(): State<Int> {
        return colorState.collectAsStateWithLifecycle()
    }

    val colors: ThemeColors
    @Composable
    get() {
        return LocalThemeColors.current
    }

}

@Composable
fun AppThemeBox(
    content: @Composable () -> Unit
) {

    val colorThemeValue = AppTheme.getColorState().value
    val colorTheme = when(colorThemeValue){
        0 -> lightThemeColor
        1 -> darkThemeColor
        else -> lightThemeColor
    }

    CompositionLocalProvider(LocalThemeColors provides colorTheme){
        MaterialTheme(
            typography = Typography,
            content = content
        )
    }
}

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)