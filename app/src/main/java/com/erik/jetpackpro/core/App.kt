package com.erik.jetpackpro.core

import android.app.Application
import com.erik.jetpackpro.di.appModules
import com.erik.jetpackpro.di.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startInjection()
    }

    private fun startInjection(){
        val moduleList = listOf(appModules, viewModelModules)

        startKoin{
            androidContext(this@App)
            modules(moduleList)
        }
    }
}