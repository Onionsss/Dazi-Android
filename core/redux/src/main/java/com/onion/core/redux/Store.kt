package com.onion.core.redux

import kotlinx.coroutines.flow.MutableStateFlow
import org.jetbrains.annotations.VisibleForTesting
import org.json.JSONObject
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: Store
 * Author: admin by 张琦
 * Date: 2024/6/18 23:18
 * Description:
 */
open class Store<S: State>(
    states: S,
    val reducers: List<Reducer<S>>,
    val sideEffects: List<SideEffect<S>>,
    val pluginName: String
){

    @Suppress("LeakingThis")
    private val info = StoreInfo(this,ReduxLog)

    private var _initialized = false

    var initialised: Boolean
        get() = _initialized
        set(value) {
            if(!_initialized && value){
                _initialized = true
                info.displayInfo()
            }
        }

    val subscriptions = ConcurrentHashMap<Int,Subscription<S>>()
    val observers = CopyOnWriteArrayList<Observer>()

    private var stateMap = hashMapOf<String,JSONObject>()
    private var _states = states

    val lock = Any()

    val states
        get(): StateGroup<*> = _states as StateGroup<*>

    fun dispatch(action: Action) = synchronized(lock){
        if(!initialised){
            ReduxLog.stepIn("Attempt made to <${action.tag()}>")
            ReduxLog.msg("Skipping... Store is not yet initialised")
            return
        }

        ReduxLog.stepIn("Dispatching <${action.tag()}>")

        val newAction = applySideEffects(action)

        if(newAction != action){
            ReduxLog.stepIn("Dispatching <${newAction.tag()}>")
        }
        val newState = applyReducer(newAction)

        if(newState == _states){
            ReduxLog.close("警告 No changes", lvl = 1)
            return
        }

        _states = newState
        observers.forEach{ observer -> observer()}
    }

    private fun applyReducer(action: Action): S =
        reducers.fold(_states) { acc,reducer -> reducer(acc,action)}

    private fun applySideEffects(action: Action): Action =
        composeSideEffects(sideEffects,0).invoke(this,action)

    private fun composeSideEffects(effects: List<SideEffect<S>>,index: Int): Next<S> =
        if(index == effects.size){
            { _,action -> action }
        }else{
            { store,action ->
                effects[index](
                    store,
                    action,
                    composeSideEffects(effects,index + 1)
                )
            }
        }

    private fun next(index: Int): Next<S> {
        if(index == sideEffects.size){
            return { _,action -> action }
        }

        return { store,action -> sideEffects[index].invoke(store,action,next(index + 1))}
    }

    inline fun <reified T: State> unsubscribe(mutableState: MutableStateFlow<T>){
        synchronized(lock){
            val stateKey = mutableState.hashCode()
            subscriptions[stateKey]?.let {  subscription ->
                subscription.unsubscribe.invoke()
                subscriptions.remove(stateKey)
                ReduxLog.msg("( ${subscriptions.size} active subscriptions")
            }
        }
    }

    inline fun <reified T: State> subscribe(
        mutableState: MutableStateFlow<T>
    ) = subscribe(mutableState) { old,new ->
        val stateToUpdate = old::class.tag()
        val changesInfo = "Updating <$stateToUpdate>"
        ReduxLog.close(changesInfo,lvl = 1)
        mutableState.value = new
    }

    inline fun <reified T: State> subscribe(
        mutableState: MutableStateFlow<T>,
        crossinline predicate: (T) -> Boolean
    ) = subscribe(mutableState){ old,new ->
        if(predicate(new)){
            val stateToUpdate = old::class.tag()
            val changesInfo = "Updating <$stateToUpdate>"
            ReduxLog.close(changesInfo,lvl = 1)
            mutableState.value = new
        }
    }

    inline fun <reified T: State> subscribeSingle(
        mutableState: MutableStateFlow<T>
    ){
        synchronized(lock){
            ReduxLog.msg("( ${subscriptions.size} removing pending subscriptions: ${subscriptions.size})")
            subscriptions.values.forEach { subscription ->
                subscription.unsubscribe.invoke()
            }
            subscriptions.clear()
            subscribe(mutableState)
        }
    }

    inline fun <reified T: State> subscribe(
        mutableState: MutableStateFlow<T>,
        noinline block:  InlineObserver<T>
    ){
        val stateName = T::class.tag()
        val key = mutableState.hashCode()
        ReduxLog.stepIn("Subscribing to <${stateName}>")
        val observer = {
            val update = this.states.verifyUpdates<T>()
            if(update != null){
                val oldState = mutableState.value
                val newState = this.states.get<T>()
                val different = newState !== oldState
                val stateToUpdate = oldState::class.tag()
                if(different){
                    block.invoke(oldState,newState)
                }else{
                    ReduxLog.stepIn("( No changes into <${stateToUpdate}> )")
                }
            }
        }

        synchronized(lock){
            unsubscribe(mutableState)
            observers.add(observer)
            val key = mutableState.hashCode()
            subscriptions[key] = Subscription(this){
                ReduxLog.close("Unsubscribing from <${T::class.tag()}>")
                synchronized(lock){
                    observers.remove(observer)
                }
            }
            ReduxLog.msg("( ${subscriptions.size} active subscriptions )")
        }
    }

    @Suppress("ReturnCount")
    fun calculateStateDiff(stateKey: String, currentState: JSONObject): JSONObject {
        //TODO
        return JSONObject()
    }
}

