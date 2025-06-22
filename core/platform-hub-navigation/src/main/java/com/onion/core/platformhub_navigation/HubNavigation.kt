package com.onion.core.platformhub_navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import com.onion.core.platformhub.core.Plugin
import com.onion.core.platformhub.core.models.BaseMessage

/**
 * author : Qi Zhang
 * date : 2025/6/17
 * description :
 */
sealed class HubNavigation {

    data class ComposeNavigation(
        val compose: @Composable () -> Unit
        ) : HubNavigation()

    data class ComposeNavGraphBuilder(
        private val nav: NavGraphBuilder.() -> Unit
    ): HubNavigation(){

        fun build(builder: NavGraphBuilder){
            nav(builder)
        }

    }
}

interface HubNavigationCall{

    fun onNavigation(message: BaseMessage.NavigationQuery): HubNavigation

}

fun Plugin.callNavigation(message: BaseMessage.NavigationQuery): HubNavigation?{
    if(this is HubNavigationCall){
        return onNavigation(message)
    }

    return null
}