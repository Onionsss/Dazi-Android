package com.onion.core.common.di

import javax.inject.Qualifier

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: Coroutines
 * Author: admin by 张琦
 * Date: 2024/6/29 14:10
 * Description:
 */
@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DefaultDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainImmediateDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DaziCoroutineScope

