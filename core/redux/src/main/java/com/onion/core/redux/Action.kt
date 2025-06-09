package com.onion.core.redux

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: Action
 * Author: admin by 张琦
 * Date: 2024/6/17 22:30
 * Description:
 */
interface Action {
    val type: ActionType
        get() = ActionType.Event
}

enum class ActionType {

    Navigation,Event

}