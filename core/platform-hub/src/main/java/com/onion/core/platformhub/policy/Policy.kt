package com.onion.core.platformhub.policy

import com.onion.core.platformhub.policy.model.PolicyPublish
import com.onion.core.platformhub.policy.model.PolicyReceive
import com.onion.core.platformhub.policy.model.PolicySend
import com.onion.core.platformhub.policy.model.PolicySubscribe

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: Policy
 * Author: admin by 张琦
 * Date: 2024/6/17 23:55
 * Description:
 */
interface Policy {

    val name: String

    val publish: PolicyPublish

    val subscribeOnDemand: PolicySubscribe

    val subscribeOnStartup: PolicySubscribe

    val send: PolicySend

    val receive: PolicyReceive

}