package com.onion.core.platformhub.handler

import com.onion.core.platformhub.core.models.BaseMessage

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: IPluginCommandHandler
 * Author: admin by 张琦
 * Date: 2024/6/29 15:11
 * Description:
 */
interface IPluginCommandHandler: IPluginHandler{

    fun command(command: BaseMessage.Command)

}