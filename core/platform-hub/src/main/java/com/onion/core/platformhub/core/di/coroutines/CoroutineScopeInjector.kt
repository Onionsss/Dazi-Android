package com.onion.core.platformhub.core.di.coroutines

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: CoroutineScopeInjector
 * Author: admin by 张琦
 * Date: 2024/6/17 23:33
 * Description:
 */
class CoroutineScopeInjector {

    fun provideCoroutineScopeProvider(dispatcher: CoroutineDispatcher) =
        CoroutineScopeProvider(dispatcher)

    inline fun <reified T: Any> inject(dispatcher: CoroutineDispatcher): T{
        return (if(T::class == CoroutineScopeProvider::class){
            provideCoroutineScopeProvider(dispatcher)
        }else{
            throw IllegalArgumentException("Unknownclass:: ${T::class}")
        }) as T
    }
}