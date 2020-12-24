package com.gaoyin.app

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.idan.frame.KoinGlobal

import com.idan.login.logic.di.loginModule
import com.idan.home.logic.di.mainModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        ARouter.init(this)
        val modules = arrayListOf(loginModule, mainModule)
        KoinGlobal.initKoin(this,modules)
    }
}