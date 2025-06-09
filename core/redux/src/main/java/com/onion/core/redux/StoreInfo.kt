package com.onion.core.redux

import kotlin.reflect.KCallable

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: StoreInfo
 * Author: admin by 张琦
 * Date: 2024/6/18 23:26
 * Description:
 */
class StoreInfo<STATE: State, STORE: Store<STATE>>(
    private val store: STORE,
    private val logger: ReduxLog
){

    private data class MapOfStates(
        val groups: Int,
        val size: Int
    )

    private fun tree(stateGroup: StateGroup<*>){
        val level = 0
        extractGroups(group = stateGroup){ group, lvl ->
            val currentLvl = lvl
            ReduxLog.setup()
        }
    }

    fun displayInfo(){
        val data = infoMap()
        val range = 0..35
        ReduxLog.open(range.repeat("︎◼️"))
        ReduxLog.msg(" Starting \"${ReduxLog.getScope()}\" Store.")
        ReduxLog.close(range.repeat("︎◼️"))
        ReduxLog.space()
        ReduxLog.stepIn("<${store::class.tag()}> activated! \uD83D\uDE80")
        ReduxLog.stepIn("Groups: ${data.groups} -- States: ${data.size}")
        if (ReduxLog.shouldDisplayTree) {
            tree(store.states)
        }
        ReduxLog.space()
        ReduxLog.stepIn("Info:")
        ReduxLog.space()
        ReduxLog.msg("${store.reducers.size} reducers: ${store.reducers.map { (it as KCallable<*>).name }}")
        ReduxLog.msg("${store.sideEffects.size} sideEffects: ${store.sideEffects.map { it::class.tag() }}")
        ReduxLog.printOptions()
        ReduxLog.space()
        ReduxLog.close(range.repeat("︎◼️"))
        ReduxLog.space()
    }

    private fun extractGroups(
        lvl: Int = 1,
        group: StateGroup<*>,
        onGroupFound: (group: StateGroup<*>, lvl: Int) -> Unit
    ){
        onGroupFound.invoke(group,lvl)
        if(lvl < 5){
            val found = group.list.filterIsInstance<StateGroup<*>>()
            found.forEach { g ->
                extractGroups(lvl = lvl + 1,group = g,onGroupFound)
            }
        }
    }

    private fun infoMap(): MapOfStates = store.states.let{
        var states = 0
        var groups = 0
        extractGroups(group = it){ group,_ ->
            groups++
            val onlyStates = group.list.filterNot { s -> s is StateGroup<*> }
            states += onlyStates.size
        }
        return MapOfStates(groups,states)
    }
}