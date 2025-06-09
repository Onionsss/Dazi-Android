package com.onion.core.platformhub.extra

import com.onion.core.common.http.BaseResult
import com.onion.core.platformhub.core.Plugin
import com.onion.core.platformhub.core.models.BaseMessage
import com.onion.core.platformhub.core.models.EventStreamOutput
import com.onion.core.platformhub.core.util.objectFromJson
import kotlinx.coroutines.flow.SharedFlow
import org.json.JSONObject

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: PluginExt
 * Author: admin by 张琦
 * Date: 2024/6/29 15:04
 * Description:
 */
fun Plugin.command(command: BaseMessage.Command): Plugin{
    messageSender.send(command,this)
    return this
}

fun Plugin.query(query: BaseMessage.Query): JSONObject {
    return messageSender.send(query,this)
}

fun Plugin.subscribe(stream: BaseMessage.EventStream): SharedFlow<EventStreamOutput> {
    return messageSender.subscribeOnDemand(stream,this)
}

inline fun <reified T> Plugin.queryResult(query: BaseMessage.Query, needOriginJsonObject: Boolean = false): BaseResult<T>{
    val obj = query(query)
    val str = obj.toString()
    return try {
        objectFromJson<BaseResult<T>>(str).apply {
            if(needOriginJsonObject){
                jsonObj = str
            }
        }
    }catch (e: Exception){
        BaseResult(-1,"",null)
    }
}

inline fun <reified T> EventStreamOutput.toBean(): T{
    return objectFromJson(this.result.getOrNull()!!.toString())
}