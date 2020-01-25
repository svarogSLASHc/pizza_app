package com.treewall.av.pizzaapp.data.logger

import com.treewall.av.pizzaapp.domain.authorization.logger.BaseLogger
import timber.log.Timber

class TimberLogger : BaseLogger {
    override fun e(t: Throwable, message: String, vararg args: Any) = Timber.e(t, message, args)

    override fun d(message: String, vararg args: Any) = Timber.d(message, args)

    override fun e(t: Throwable) = Timber.e(t)
}