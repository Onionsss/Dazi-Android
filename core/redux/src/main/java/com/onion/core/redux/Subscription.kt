package com.onion.core.redux

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: SubScription
 * Author: admin by 张琦
 * Date: 2024/6/17 22:37
 * Description:
 */

typealias InlineObserver<State> = (old: State,new: State) -> Unit
typealias Observer = () -> Unit
typealias Unsubscribe = () -> Unit

data class Subscription<S: State>(val store: Store<S>,val unsubscribe: Unsubscribe)