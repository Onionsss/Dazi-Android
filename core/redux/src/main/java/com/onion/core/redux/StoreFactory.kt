package com.onion.core.redux

import java.util.logging.Logger

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: StateFactory
 * Author: admin by 张琦
 * Date: 2024/6/23 22:33
 * Description:
 */
object StoreFactory {

    private lateinit var logger: Logger

    fun initLogger(logger: Logger){

        StoreFactory.logger = logger

    }

    fun <States: State> makeStore(
        initialStates: States,
        reducers: List<Reducer<States>>,
        sideEffects: List<SideEffect<States>>,
        pluginName: String
    ): Store<States>{
        return Store(
            states = initialStates,
            reducers = reducers,
            sideEffects = sideEffects,
            pluginName = pluginName
        )
    }

}