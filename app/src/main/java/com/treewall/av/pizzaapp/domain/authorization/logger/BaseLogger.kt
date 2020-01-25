package com.treewall.av.pizzaapp.domain.authorization.logger

interface BaseLogger {
    fun e(t: Throwable, message: String, vararg args: Any)

    fun d(message: String, vararg args: Any)

    fun e(t: Throwable)
}