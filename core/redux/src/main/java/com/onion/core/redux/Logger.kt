package com.onion.core.redux

import android.os.Build.VERSION_CODES.P
import timber.log.Timber

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: Logger
 * Author: admin by 张琦
 * Date: 2024/6/18 23:08
 * Description:
 */

private const val MAX_SCOPE_LENGTH: Long = 26

object ReduxLog: Timber.DebugTree(){

    private var pScopes: MutableList<String> = mutableListOf()
    private var pEnable = false
    private var pDisplayTree = false
    val shouldDisplayTree get() = pDisplayTree

    private var startL = ""
    private var rootL_ = ""
    private var nextL_ = ""
    private var endL__ = ""

    fun setup(
        scope: String? = null,
        enabled: Boolean? = null,
        displayTree: Boolean? = null,
    ){
        scope?.let { pScopes.add(it) }
        pEnable = enabled ?: pEnable
        pDisplayTree = displayTree ?: pDisplayTree
    }

    fun open(message: String?, vararg args: Any?){
        super.d("$startL $message", *args)
    }

    fun space(vararg args: Any?) = super.d(rootL_, * args)

    fun stepIn(message: String?,vararg args: Any?,lvl: Int = 0){
        val separator = (0 until  lvl).map { "$rootL_ " }.joinToString("")
        super.d("$separator$nextL_ $message", *args)
    }

    fun msg(message: String?,vararg args: Any?,lvl: Int = 0){
        val separator = (0 until  lvl).map { "$rootL_ " }.joinToString("")
        super.d("$separator$nextL_ $message", *args)
    }

    fun close(message: String?,vararg args: Any?,lvl: Int = 0){
        var prefix = endL__
        var separator = " "
        if(lvl > 0){
            prefix = rootL_
            separator = if(lvl > 1) {
                (0 until  lvl).map { " " }.joinToString(endL__)
            }else{
                " $endL__ "
            }
        }
        super.d("$prefix$separator$message", *args)
    }

    fun stepOut(level: Int?){
        val end = (0 until (level ?: 0) + 1).map { "----" }.joinToString("")
        super.d("$end")
    }
    fun printOptions(){
        msg("- displayTree ${shouldDisplayTree}")
    }

    fun getScope(): String? {
        val stacks = Thread.currentThread().stackTrace
        var scopeFound: String? = null
        var scopeIndex: Int = stacks.size
        for (scope in pScopes){
            val index = stacks.indexOfFirst { it.className.startsWith(scope) }
            if(index != -1 && index < scopeIndex){
                scopeIndex = index
                scopeFound = scope.replace(Regex("([a-z]*\\.)*"),"")
            }
        }
        return scopeFound
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if(pEnable){
            var scope = getScope()
            if(pScopes.isEmpty() || scope != null){
                requireValidScope(scope)
                scope = formatScope(scope)
                val mg = "Redux${scope.let { "::$it" }}"
                Timber.tag(mg).log(
                    priority,
                    message
                )
            }
        }
    }

    private fun requireValidScope(scope: String?){
        if(scope != null){
            require(scope.length <= MAX_SCOPE_LENGTH){
                "\n\nScope name \"$scope\" is too long!\n" +
                        "Max length is $MAX_SCOPE_LENGTH\n"
            }
        }
    }

    private fun formatScope(scope: String?): String?{
        if(scope != null && scope.length < MAX_SCOPE_LENGTH){
            val to = (MAX_SCOPE_LENGTH - scope.length).toInt()
            return scope + (0 .. to).consoleSpaces()
        }
        return scope
    }
}