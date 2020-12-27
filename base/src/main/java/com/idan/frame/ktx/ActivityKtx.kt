package com.idan.frame.ktx

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.idan.frame.AppGlobal
import com.idan.frame.R

fun Any.d() {
    Log.d("DEBUG", "$this")
}

fun Any?.e(tag: String? = null) {

    Log.e(tag ?: "ERROR ========> ", "$this")
}

fun Any?.show() {
    this?.let {
        Toast.makeText(AppGlobal.getContext(), "$this", Toast.LENGTH_SHORT).show()
    }
}

/**
 * 跳转到指定应用的首页
 */
@SuppressLint("NewApi")
fun startActivity(packageName: String) {
    AppGlobal.getContext().apply {
        startActivity(packageManager.getLaunchIntentForPackage(packageName))
    }
}

/**
 * 跳转到指定应用的指定页面
 */
fun startActivity(packageName: String, activityDri: String) {
    AppGlobal.getContext().apply {
        startActivity(Intent().apply {
            component = ComponentName(packageName, activityDri)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }
}

/**
 * @param showAllText 显示完整的内容,默认超出一行显示省略号。   imgRes:需要显示的大图资源，默认不显示
 * @param imgRes != -1时，showAllText参数无效。（setStyle被覆盖）
 */
fun showNotice(
    title: String,
    content: String,
    showAllText: Boolean = false,
    imgRes: Int = -1,
    block: (() -> Intent)? = null
) {
    AppGlobal.getContext().apply {
        var notiManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //8.0只有需要创建Channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notiManager.createNotificationChannel(
                NotificationChannel(
                    "KtDemo",
                    "KtDemo",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            )
        }

        var pendingIntent: PendingIntent? = null

        block?.let {
            pendingIntent = PendingIntent.getActivity(this, 0, it(), 0)
        }

        var notification = NotificationCompat.Builder(this, "KtDemo").apply {
            //设置标题
            setContentTitle(title)
            if (showAllText) {
                //设置内容的样式，展示所有
                setStyle(NotificationCompat.BigTextStyle().bigText(content))
            } else {
                //设置内容（默认只展示一行，超出部分显示省略号）
                setContentText(content)
                if (imgRes != -1) {
                    //设置样式，展示一张图片
                    setStyle(
                        NotificationCompat.BigPictureStyle()
                            .bigPicture(BitmapFactory.decodeResource(resources, imgRes))
                    )
                }
            }
            //点击之后自动关闭
            setAutoCancel(true)
            //设置小图标
            setSmallIcon(R.drawable.ic_android_black)
            //设置大图标
            setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher_round))
            if (null != pendingIntent) {
                //设置跳转意图
                setContentIntent(pendingIntent)
            }
        }.build()
        notiManager.notify(1, notification)
    }
}
