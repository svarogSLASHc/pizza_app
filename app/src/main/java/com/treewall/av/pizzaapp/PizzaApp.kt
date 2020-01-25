package com.treewall.av.pizzaapp

import android.app.Application
import com.treewall.av.pizzaapp.di.dataModule
import com.treewall.av.pizzaapp.di.domainModule
import com.treewall.av.pizzaapp.di.presentationModule
import com.treewall.av.pizzaapp.di.serviceModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree

class PizzaApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@PizzaApp)
            modules(listOf(dataModule, serviceModule, presentationModule, domainModule))
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}