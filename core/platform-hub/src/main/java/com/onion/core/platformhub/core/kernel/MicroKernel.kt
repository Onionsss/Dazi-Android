package com.onion.core.platformhub.core.kernel

import com.onion.core.platformhub.core.Plugin
import com.onion.core.platformhub.core.models.BaseMessage

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: MicroKernel
 * Author: admin by 张琦
 * Date: 2024/6/23 23:57
 * Description:
 */
internal interface MicroKernel {

    fun init()

    fun postInit()

    fun validate()

    fun postValidate()

    fun setupSubscriptions()

    interface MessageSender{
        fun send(command: BaseMessage.Command,plugin: Plugin)

        fun send(query: BaseMessage.Query,plugin: Plugin)

        fun subscribe(eventStream: BaseMessage.EventStream,subscriber: Plugin)
    }

    interface Validator{
        fun runChecks()

        fun checkForOverlappingMessage()

        fun checkPluginNameFormat()

        fun checkForDuplicateMessage()

        fun checkMessageNameFormat()

        fun checkMultipleRecipientMessage()

        fun checkForMultipleRecipients()

        fun checkForDuplicatePluginNames()

        fun checkForUnusedPlugins()

        fun checkReceiverPlugin()

    }
}