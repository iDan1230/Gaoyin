package com.gaoyin.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.idan.login.ui.login.LoginActivity

class LaunchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
//        ARouter.getInstance().build("/login/login").navigation()
//        ARouter.getInstance().build("/main/main").navigation()
//        ARouter.getInstance().build("/login/login").navigation(this,object :NavigationCallback{
//            override fun onLost(postcard: Postcard?) {
//                "onLost".e()
//            }
//
//            override fun onFound(postcard: Postcard?) {
//                "onFound".e()
//            }
//
//            override fun onInterrupt(postcard: Postcard?) {
//                "onInterrupt".e()
//            }
//            //到达目标
//            override fun onArrival(postcard: Postcard?) {
//                "onArrival".e()
//                finish()
//            }
//        })
        startActivity(Intent(this,LoginActivity::class.java))
    }
}