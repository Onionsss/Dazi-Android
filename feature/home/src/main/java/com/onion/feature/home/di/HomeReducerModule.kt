package com.onion.feature.home.di

import com.onion.feature.home.redux.refresh.HomeRefreshReducer
import com.onion.feature.home.store.HomeStoreActions
import com.onion.feature.home.store.HomeStoreStates
import com.toggl.komposable.architecture.Reducer
import com.toggl.komposable.extensions.combine
import com.toggl.komposable.extensions.pullback
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: HomeReducerModule
 * Author: admin by 张琦
 * Date: 2024/11/1 17:59
 * Description:
 */
@Module
@InstallIn(SingletonComponent::class)
internal object HomeReducerModule {

    @Provides
    @Singleton
    internal fun provideGlobalReducer(
        homeRefreshReducer: HomeRefreshReducer
    ): Reducer<HomeStoreStates,HomeStoreActions>{
        return combine(homeRefreshReducer.pullback(
            mapToLocalState = { it.homeRefreshState },
            mapToLocalAction = { (it as? HomeStoreActions.HomeRefreshActions)?.action },
            mapToGlobalState = { globalState, homeRefreshState ->
                globalState.copy(
                    homeRefreshState = homeRefreshState
                )
            },
            mapToGlobalAction = { (HomeStoreActions.HomeRefreshActions(it)) }
        ))
    }

}