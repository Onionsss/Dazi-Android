package com.onion.core.ext

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.clickable

import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

/**
 * author : Qi Zhang
 * date : 2025/7/9
 * description :
 */
fun Modifier.debounceClickable(
    intervalMillis: Long = 600L,
    enabled: Boolean = true,
    onClick: () -> Unit
): Modifier = composed {
    var lastClickTime by remember { mutableStateOf(0L) }

    this.clickable(enabled = enabled) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime >= intervalMillis) {
            lastClickTime = currentTime
            onClick()
        }
    }
}