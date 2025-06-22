package com.onion.core.platformhub.core.extensions

import com.onion.core.platformhub.core.Plugin
import com.onion.core.platformhub.core.models.BaseMessage
import com.onion.core.platformhub.core.models.Message
import com.onion.core.platformhub.policy.model.CorrelationMessage
import com.onion.core.platformhub.policy.model.PolicyMessage
import com.onion.core.platformhub.policy.model.PolicyReceive
import com.onion.core.platformhub.policy.model.PolicySend
import java.sql.Timestamp

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: Extensions
 * Author: admin by 张琦
 * Date: 2024/6/23 23:37
 * Description:
 */
internal fun List<PolicyMessage>.toMessageNames() = map { policyMessage ->
    policyMessage.messageName
}

internal fun Message.toPoliceMessage() = when(this){
    is BaseMessage.EventStream -> PolicyMessage.EventStreamPolicy(messageName,pluginName)
    is BaseMessage.Query -> PolicyMessage.QueryPolicy(messageName,pluginName)
    is BaseMessage.Command -> PolicyMessage.CommandPolicy(messageName,pluginName)
    is BaseMessage.NavigationQuery -> PolicyMessage.NavigationQueryPolicy(messageName,pluginName)
    else -> PolicyMessage.NavigationQueryPolicy(messageName,pluginName)
}

internal fun Message.toLog() = "$this with uuid=$uuid"

internal fun PolicyReceive.allMessages() = commands + queries + navigationQueries

internal fun PolicySend.allMessages() = commands + queries + navigationQueries

internal fun PolicyMessage.messageId() = "$pluginName.$messageName"
internal fun PolicyMessage.messageId(plugin: Plugin) = "${plugin.name()}.$messageName"

fun BaseMessage.toCorrelationMessage() = CorrelationMessage(
    messageName = messageName,
    pluginName = pluginName,
    uuid = uuid
)

internal fun Long.toTimestamp(): String = Timestamp(this).toString()