package com.onion.core.ext

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry

/**
 * author : Qi Zhang
 * date : 2025/6/16
 * description :
 */
@Composable
inline fun <reified VM : ViewModel> hiltViewModelKey(
    viewModelStoreOwner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner .current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    },
    modelKey: String? = null,
): VM {
    val factory = createHiltViewModelFactory(viewModelStoreOwner)
    return viewModel(viewModelStoreOwner, factory = factory, key = modelKey)
}

@Composable
inline fun <reified VM : ViewModel> hiltViewModelKey(
    viewModelStoreOwner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner .current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    },
    id: String? = null,
    extras: CreationExtras = if (viewModelStoreOwner is HasDefaultViewModelProviderFactory) {
        viewModelStoreOwner.defaultViewModelCreationExtras
    } else {
        CreationExtras.Empty
    }
): VM {
    val factory = createHiltViewModelFactory(viewModelStoreOwner)
    return viewModel(viewModelStoreOwner, factory = factory, key = id)
}

@Composable
@PublishedApi
internal fun createHiltViewModelFactory(
    viewModelStoreOwner: ViewModelStoreOwner
): ViewModelProvider.Factory? = if (viewModelStoreOwner is NavBackStackEntry) {
    HiltViewModelFactory(
        context = LocalContext.current,
        navBackStackEntry = viewModelStoreOwner
    )
} else {
    // Use the default factory provided by the ViewModelStoreOwner
    // and assume it is an @AndroidEntryPoint annotated fragment or activity
    null
}