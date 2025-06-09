package com.onion.core.platformhub.core.kernel

import com.onion.core.platformhub.core.Plugin
import com.onion.core.platformhub.core.models.BaseMessage

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: NessageSenderImpl
 * Author: admin by 张琦
 * Date: 2024/6/24 0:05
 * Description:
 */
internal class MessageSenderImpl: MicroKernel.MessageSender {
    override fun send(command: BaseMessage.Command, plugin: Plugin) {
        TODO("Not yet implemented")
    }

    override fun send(query: BaseMessage.Query, plugin: Plugin) {
        TODO("Not yet implemented")
    }

    override fun subscribe(eventStream: BaseMessage.EventStream, subscriber: Plugin) {
        TODO("Not yet implemented")
    }
}