package com.idan.frame.http

import androidx.lifecycle.liveData
import com.alibaba.fastjson.JSON
import com.idan.frame.ktx.e
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.*
import java.lang.Exception
import java.lang.RuntimeException
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object ServiceCreator {

//    private const val BASE_URL = "http://10.0.213.214:9100/"
    private const val BASE_URL = "http://182.140.240.104:9001/"

    private val okHttpClient = OkHttpClient.Builder()
        .readTimeout(10 * 1000, TimeUnit.MILLISECONDS)
        .writeTimeout(10 * 1000, TimeUnit.MILLISECONDS)
        .cookieJar(PersistenceCookieJar())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ApiGsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)
}

inline fun <reified T> create(): T = ServiceCreator.create(T::class.java)

/**
 * 给 Call 增加一个顶层的扩展函数，
 */
suspend fun <T> Call<T>.await(): T {
    return suspendCoroutine { continuation ->
        enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val body = response.body()
                if (null != body) {
                    continuation.resume(body)
                } else {
                    continuation.resumeWithException(RuntimeException("response body is null"))
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                continuation.resumeWithException(t)
            }
        })
    }
}

fun handleExcption(e: Throwable): String {
    var errorString = e.message ?: "请求失败"
    try {
        e.let {
            if (e.message?.startsWith("Bad Request")!! || e.message?.startsWith("HTTP 400 Bad Request")!!) {
                (e as HttpException).response()?.apply {
                    var jsonObject = JSON.parseObject(errorBody()?.string())
                    jsonObject.toJSONString().e()
                    if (jsonObject.containsKey("msg")) {
                        errorString = jsonObject.getString("msg")
                    }
                }
            }
        }
    } catch (e: Exception) {
        errorString = e.message ?: "解析异常"
    }
    errorString.e()
    return errorString
}
