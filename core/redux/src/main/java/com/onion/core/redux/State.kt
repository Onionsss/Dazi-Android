package com.onion.core.redux

import com.onion.core.platformhub.core.util.objectToJson
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import org.json.JSONObject
import kotlin.reflect.KClass

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: State
 * Author: admin by 张琦
 * Date: 2024/6/4 23:32
 * Description:
 */
val json = Json { encodeDefaults = true }

open class State {

    @OptIn(ExperimentalSerializationApi::class)
    internal fun toJsonObject(): JSONObject = JSONObject(
        objectToJson(
            json.encodeToJsonElement(
                json.serializersModule.serializer(this::class.java),
                this
            )
        )
    )

}

abstract class StateGroup<T: StateGroup<T>>(
    internal var list: MutableList<State> = mutableListOf()
): State(){

    internal var updates = mutableListOf<State>()

    fun <T: State> verifyUpdates(_in: KClass<T>): State? = synchronized(updates){
        val indexFound = updates.indexOfFirst { _in.isInstance(it) }
        val update = if (indexFound != -1) updates.removeAt(indexFound) else null
        if(update == null){
            list.filterIsInstance<StateGroup<*>>().forEach {
                val result = it.verifyUpdates(_in)
                if(result != null) return result
            }
        }
        return update
    }

    inline fun <reified T: State> verifyUpdates(): State? = verifyUpdates(T::class)

    @Suppress("NestedBlockDepth")
    fun <T: State> get(_in: KClass<T>): State? {
        val group = this::class.tag()
        val state = _in.tag()
        try{
            val stateFound = list.singleOrNull{ _in.isInstance(it) }
            if(stateFound == null){
                list.filterIsInstance<StateGroup<*>>().forEach {
                    val result = it.get(_in)
                    if(result != null) return result
                }
            }
            return stateFound
        }catch (error: Exception){
            val log = "<$state> must exist and be unique in <$group>\n"
            val end = "----------------------------------------------"
            ReduxLog.e(error,"%s%s",log,end)
            throw  error
        }
    }

    inline fun <reified T: State> get(): T = get(T::class) as T

    @Suppress("NestedBlockDepth","LoopWithTooManyJumpStatements")
    fun <IN: State> update(
        _in: KClass<IN>,
        state: IN
    ): T{
        var stateFound = false
        val statesCopy = list.toMutableList()
        val updatesCopy = updates.toMutableList()
        for (child in list){
            when{
                _in.isInstance(child) -> {
                    stateFound = true
                    val index = statesCopy.indexOf(child)
                    statesCopy.removeAt(index)
                    statesCopy.add(index,state)
                    updatesCopy.add(state)
                    break
                }
                child is StateGroup<*> -> {
                    @Suppress("UNCHECKED_CAST")
                    val updated = (child as T).update(_in,state)
                    if(updated !== child){
                        stateFound = true
                        val index = statesCopy.indexOf(child)
                        statesCopy.removeAt(index)
                        statesCopy.add(index,updated)
                        break
                    }
                }
            }
        }
        if(!stateFound){
            val stateName = _in.tag()
            val storeName = Store::class.tag()
            throw ReduxStateException(
                "$stateName is not declared in $storeName"
            )
        }
        list = statesCopy
        updates = updatesCopy
        val clone = newInstance()
        clone.list = list
        clone.updates = updates
        return clone
    }

    inline fun <reified IN: State> update(state: IN): T = update(IN::class,state)

    abstract fun newInstance(): T
}