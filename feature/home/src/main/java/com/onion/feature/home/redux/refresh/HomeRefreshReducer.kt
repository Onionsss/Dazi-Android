package com.onion.feature.home.redux.refresh

import com.toggl.komposable.architecture.ReduceResult
import com.toggl.komposable.architecture.Reducer
import com.toggl.komposable.extensions.withoutEffect
import javax.inject.Inject

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: HomeRefreshReducer
 * Author: admin by 张琦
 * Date: 2024/11/15 21:36
 * Description:
 */
internal class HomeRefreshReducer @Inject constructor(): Reducer<HomeRefreshState,HomeRefreshAction> {

    override fun reduce(
        state: HomeRefreshState,
        action: HomeRefreshAction
    ): ReduceResult<HomeRefreshState, HomeRefreshAction> {
        return when(action){
            is HomeRefreshAction.Refresh -> {
                state.copy(refresh = true,version = state.version + 1).withoutEffect()
            }
            else -> {
                state.withoutEffect()
            }
        }
    }
}