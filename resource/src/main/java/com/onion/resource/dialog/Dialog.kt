package com.onion.resource.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: Dialog
 * Author: admin by 张琦
 * Date: 2024/6/30 12:04
 * Description:
 */
@Composable
fun AddAlterDialog(alertDialog: MutableState<Boolean>, onClose: () -> Unit) {
    if (alertDialog.value) {
        AlertDialog(
            onDismissRequest = { alertDialog.value = false },
            title = { Text(text = "标题") },
            text = {
                Text(
                    text = "文本内容"
                )
            }, confirmButton = {
                TextButton(
                    onClick = onClose
                ) {
                    Text(text = "确认")
                }
            }, dismissButton = {
                TextButton(onClick = { alertDialog.value = false }) {
                    Text(text = "取消")
                }
            })
    }
}