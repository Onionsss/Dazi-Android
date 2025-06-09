package com.onion.core.platformhub.policy.model

import kotlinx.serialization.Serializable

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: PolicyMessage
 * Author: admin by 张琦
 * Date: 2024/6/17 23:58
 * Description:
 */

@Serializable
data class PolicyPublish(
    val eventStreams: List<PolicyMessage.EventStreamPolicy> = emptyList()
)

@Serializable
data class PolicySubscribe(
    val eventStreams: List<PolicyMessage.EventStreamPolicy> = emptyList()
)

@Serializable
data class PolicySend(
    val commands: List<PolicyMessage.CommandPolicy> = emptyList(),
    val queries: List<PolicyMessage.QueryPolicy> = emptyList(),
    val navigationQueries: List<PolicyMessage.NavigationQueryPolicy> = emptyList()
)

@Serializable
data class PolicyReceive(
    val commands: List<PolicyMessage.CommandPolicy> = emptyList(),
    val queries: List<PolicyMessage.QueryPolicy> = emptyList(),
    val navigationQueries: List<PolicyMessage.NavigationQueryPolicy> = emptyList()
)

@Serializable
sealed class PolicyMessage{

    abstract val messageName: String
    abstract val pluginName: String?

    @Serializable
    data class EventStreamPolicy(
        override val messageName: String,
        override val pluginName: String?
    ): PolicyMessage()

    @Serializable
    data class CommandPolicy(
        override val messageName: String,
        override val pluginName: String?
    ): PolicyMessage()

    @Serializable
    data class QueryPolicy(
        override val messageName: String,
        override val pluginName: String?
    ): PolicyMessage()

    @Serializable
    data class NavigationQueryPolicy(
        override val messageName: String,
        override val pluginName: String?
    ): PolicyMessage()
}
