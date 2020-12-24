package com.idan.frame.http

import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.RuntimeException
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object ServiceCreator {

    private const val BASE_URL = "http://10.0.213.214:9100/"

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
