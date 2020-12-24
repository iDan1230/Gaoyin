package com.idan.frame

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.Module

object KoinGlobal {

    fun initKoin(app: Application, moduless: ArrayList<Module>) {
        startKoin {
            androidLogger()
            androidContext(app)
//            moduless.add(AppModule.appModule)
            modules(moduless)
        }
    }
}