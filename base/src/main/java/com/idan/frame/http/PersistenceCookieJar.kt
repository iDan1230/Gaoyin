package com.idan.frame.http

import com.idan.frame.ktx.e
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class PersistenceCookieJar : CookieJar {
//    val cache = mutableListOf<Cookie>()
    val cacheMap = mutableMapOf<String, MutableList<Cookie>>()
    //使用map为了防止得到的cookie信息不规范照成重复存储
    override fun saveFromResponse(url: HttpUrl, cookies: MutableList<Cookie>) {
        cacheMap[url.host()] = cookies
//        cache.addAll(cookies)
        "CookieJar ------------>${cookies.toString()}".e()
    }

    override fun loadForRequest(url: HttpUrl): MutableList<Cookie> {
//        var invalidCookies = mutableListOf<Cookie>()
//        var validCookies = mutableListOf<Cookie>()
//        for (cookie in cache){
//            //当前时间大于cookie的过期时间时
//            if (cookie.expiresAt() < System.currentTimeMillis()){
//                invalidCookies.add(cookie)
//            }else{
//                validCookies.add(cookie)
//            }
//            e("CookieJar ------------> ${cookie.expiresAt()} - ${System.currentTimeMillis()}")
//        }
//        cache.removeAll(invalidCookies)

        val cookies = cacheMap[url.host()]
        return cookies ?: mutableListOf<Cookie>()
    }
}