package com.onion.core.platformhub.policy.model

import com.onion.core.platformhub.policy.Policy
import kotlinx.serialization.Serializable

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: PolicyModel
 * Author: admin by 张琦
 * Date: 2024/6/18 0:03
 * Description:
 */
@Serializable
data class PolicyModel(
    override val name: String,
    override val publish: PolicyPublish = PolicyPublish(),
    override val subscribeOnDemand: PolicySubscribe = PolicySubscribe(),
    override val subscribeOnStartup: PolicySubscribe = PolicySubscribe(),
    override val send: PolicySend = PolicySend(),
    override val receive: PolicyReceive = PolicyReceive(),
): Policy