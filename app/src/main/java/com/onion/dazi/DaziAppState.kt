package com.onion.dazi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: DaziAppState
 * Author: admin by 张琦
 * Date: 2024/3/3 18:20
 * Description:
 */
@Composable
fun rememberDaziAppState(
): DaziAppState {
    return remember() {
        DaziAppState()
    }
}

@Stable
class DaziAppState(

) {

}