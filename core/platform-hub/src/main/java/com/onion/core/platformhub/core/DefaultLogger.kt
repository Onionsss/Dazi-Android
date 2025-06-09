package com.onion.core.platformhub.core

import com.onion.core.platformhub.core.models.LogInfo
import com.onion.core.platformhub.core.models.MessageType
import com.onion.core.platformhub.policy.model.PrivacySchemaModel
import com.onion.core.platformhub.core.util.AssetsLoader
import timber.log.Timber

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: DefaultLogger
 * Author: admin by 张琦
 * Date: 2024/6/29 14:32
 * Description:
 */
internal class DefaultLogger(assetsLoader: AssetsLoader) : Logger(assetsLoader) {

    override val loggerLevel: PrivacySchemaModel.Privacy
        get() = PrivacySchemaModel.Privacy.HIGHLY_RESTRICTED

    @Suppress("LongMethod")
    override fun log(logInfo: LogInfo) {
        val logMessage = when (logInfo) {
            is LogInfo.MessageInfo -> if (logInfo.messageType == MessageType.Query) {
                """
                ==============Message Info==============
                Sender:         ${logInfo.senderPlugin}
                Receiver:       ${logInfo.receiverPlugin}
                Message:        ${logInfo.messageName}
                Message Type:   ${logInfo.messageType.name}
                Input Payload:  ${logInfo.payload?.toString()}
                Output Payload: ${logInfo.responsePayload?.toString()}
                Timestamp:      ${logInfo.timestamp}
                ========================================
            """.trimIndent()
            } else {
                """
        ==============Message Info==============
        Sender:         ${logInfo.senderPlugin}
        Receiver:       ${logInfo.receiverPlugin}
        Message:        ${logInfo.messageName}
        Message Type:   ${logInfo.messageType.name}
        Input Payload:  ${logInfo.payload?.toString()}
        Timestamp:      ${logInfo.timestamp}
        ========================================
    """.trimIndent()
            }

            is LogInfo.EventStreamInfo -> """
        ===============Event Info===============
        Publisher:        ${logInfo.publisherPlugin}
        Subscribers:      ${logInfo.subscriberPlugins}
        Message:          ${logInfo.messageName}
        Message Type:     ${logInfo.messageType.name}
        Input Payload:    ${logInfo.payload?.toString()}
        Timestamp:        ${logInfo.timestamp}
        EventStreamResult:${logInfo.resultPayload}
        ========================================
    """.trimIndent()

            is LogInfo.ErrorInfo -> """
        ===============Error Info===============
        Sender:         ${logInfo.senderPlugin}
        Receiver:       ${logInfo.receiverPlugin}
        Message:        ${logInfo.messageName}
        Message Type:   ${logInfo.messageType.name}
        Input Payload:  ${logInfo.payload?.toString()}
        Error Message:  ${logInfo.errorMessage}
        Timestamp:      ${logInfo.timestamp}
        ========================================
    """.trimIndent()

            is LogInfo.InternalPluginAction -> """
        ===============PluginInternalAction Info===============
        Plugin Name:    ${logInfo.pluginName}
        Action Name:    ${logInfo.messageName}
        Screen Name:    ${logInfo.screenName}
        Input Payload:  ${logInfo.payload?.toString()}
        Timestamp:      ${logInfo.timestamp}
        ========================================
    """.trimIndent()
        }
        Timber.tag(TAG).d(logMessage)
    }

    companion object {
        private const val TAG = "PH-Logger"
    }
}