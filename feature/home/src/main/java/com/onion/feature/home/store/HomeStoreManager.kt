package com.onion.feature.home.store

import com.onion.feature.home.redux.refresh.HomeRefreshAction
import com.onion.feature.home.redux.refresh.HomeRefreshState
import com.toggl.komposable.architecture.Store
import javax.inject.Inject

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: HomeStoreManager
 * Author: admin by 张琦
 * Date: 2024/11/1 18:05
 * Description:
 */
internal class HomeStoreManager @Inject constructor(private val provider: HomeStoreProvider){

    val homeStore: Store<HomeStoreStates,HomeStoreActions> by lazy { provider.get() }

    val homeRefreshStore = homeStore.view<HomeRefreshState,HomeRefreshAction>(
        mapToLocalState = { it.homeRefreshState },
        mapToGlobalAction = { HomeStoreActions.HomeRefreshActions(it) }
    )

}