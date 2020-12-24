package com.idan.frame

import android.annotation.SuppressLint
import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent

object AppGlobal {

    private var app: Application? = null

    /**
     * 反射获取应用Application
     */
    @SuppressLint("PrivateApi", "DiscouragedPrivateApi")
    private fun getApp(): Application {
        if (app === null) {
            app = try {
                val activityThread = Class.forName("android.app.ActivityThread")
                val currentApplication = activityThread.getDeclaredMethod("currentApplication")
                val currentActivityThread =
                    activityThread.getDeclaredMethod("currentActivityThread")
                val current = currentActivityThread.invoke(null as Any?)
                val app = currentApplication.invoke(current)
                app as Application
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
        return app!!
    }

    fun getContext(): Context = getApp().baseContext

}