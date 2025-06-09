package com.onion.core.redux

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: SideEffect
 * Author: admin by 张琦
 * Date: 2024/6/18 23:16
 * Description:
 */
typealias Next<State> = (store: Store<State>,action: Action) -> Action
typealias SideEffect<State> = (store: Store<State>,action: Action,next: Next<State>) -> Action