package com.onion.feature.home.store

import com.onion.core.common.di.DaziCoroutineScope
import com.onion.core.common.di.DefaultDispatcher
import com.onion.core.common.di.IoDispatcher
import com.onion.core.common.di.MainDispatcher
import com.toggl.komposable.architecture.Reducer
import com.toggl.komposable.architecture.Store
import com.toggl.komposable.extensions.createStore
import com.toggl.komposable.scope.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: HomeStoreProvider
 * Author: admin by 张琦
 * Date: 2024/11/1 18:07
 * Description:
 */
@Singleton
internal class HomeStoreProvider @Inject constructor(
    @DaziCoroutineScope scope: CoroutineScope,
    @IoDispatcher ioDispatcher: CoroutineDispatcher,
    @MainDispatcher mainDispatcher: CoroutineDispatcher,
    @DefaultDispatcher computationDispatcher: CoroutineDispatcher,
): Provider<Store<HomeStoreStates, HomeStoreActions>> {

    @Inject
    lateinit var globalReducer: Reducer<HomeStoreStates,HomeStoreActions>

    override fun get(): Store<HomeStoreStates, HomeStoreActions> = homeStore

    private val homeStore: Store<HomeStoreStates, HomeStoreActions> by lazy {
        createStore(
            initialState = HomeStoreStates(),
            reducer = globalReducer,
            storeScopeProvider = { scope },
            dispatcherProvider = DispatcherProvider(
                ioDispatcher,computationDispatcher,mainDispatcher
            )
        )
    }
}