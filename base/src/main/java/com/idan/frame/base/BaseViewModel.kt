package com.idan.frame.base

import android.annotation.SuppressLint
import android.util.Base64
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.idan.frame.APP_KEY
import com.idan.frame.APP_SECRET
import com.idan.frame.AppGlobal
import com.idan.frame.http.handleExcption
import com.idan.frame.ktx.e
import com.idan.frame.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.crypto.Mac
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import kotlin.random.Random

/**
 * @Author yangzhidan
 * @Date   2020/12/24
 * @Remark VM基类
 */
open class BaseViewModel : ViewModel() {

    open val state = MutableLiveData<State>()

    val map = mapOf(
//            "app_key" to APP_KEY,
        "app_key" to "b617866c20482d133d5de66fceb37da3",
        "client_os_type" to "4",
//            "nonce" to getRandomStr(32),
        "nonce" to "b2eb45de3ecb4b9a88958942ad10b8d8",
        "server_api_version" to "1.0.0"
    )

    /**
     * 普通加载方式
     */
    fun launch(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            try {
                state.value = LoadState
                block()
                state.value = Success
            } catch (e: Throwable) {
                state.value = Failed(handleExcption(e))
            }
        }
    }

    /**
     * paging加载方式
     */
    fun <D : Any> flowData(queryPage: suspend (Int) -> PageInfo<D>) =
        Pager(PagingConfig(pageSize = 1)) {
            BaseDataSource { page -> queryPage.invoke(page) }
        }.flow

    fun <X, T> switchMap(params: LiveData<X>, fun1: () -> LiveData<T>) =
        viewModelScope.launch {
            try {
                state.value = LoadState
                Transformations.switchMap(params) {
                    fun1.invoke()
                }
                state.value = Success
            } catch (e: Throwable) {
                state.value = Failed(handleExcption(e))
            }
        }

    /**
     * 根据参数生成sig并返回最终请求使用的完成参数
     */
    fun createParams(map: Map<String, String>): MutableMap<String, String> {
        //公共字段
        val baseMap = mutableMapOf(
            "app_key" to APP_KEY,
            "client_os_type" to "2",
            "nonce" to getRandomStr(32),
            "timestamp" to System.currentTimeMillis().toString(),
            "server_api_version" to "1.0.0",
            "device_id" to getAndroidId().toByteArray().md5()
        )
        baseMap.putAll(map)
        baseMap["sig"] = createSig(baseMap.toSortedMap(), APP_SECRET) ?: ""
        baseMap.toString().e("参数----> ")
        return baseMap
    }

    fun createSig(map: MutableMap<String, String>, key: String): String? {
        if (map.isNullOrEmpty()) {
            return ""
        }

        val sb = StringBuffer()
        //排序之后再输出结果（重点在排序）
        map.toSortedMap().forEach {
            if (sb.isNotEmpty()) {
                sb.append("&")
            }
            sb.append("${it.key}=${it.value}")
        }
        return HmacSHA1Encrypt(toBase64(sb.toString())!!, key)!!.md5()
    }

    /**
     * 生成一个指定长度的随机字符串
     */
    fun getRandomStr(size: Int): String {
        val str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        val sb = StringBuffer()
        for (i in 0 until size) {
            val number = Random.nextInt(62)
            sb.append(str[number])
        }
        return sb.toString()
    }

    /**
     * 将字符串转为Base64格式
     * CRLF 这个参数看起来比较眼熟，它就是Win风格的换行符，意思就是使用CR LF这一对作为一行的结尾而不是Unix风格的LF
     * DEFAULT 这个参数是默认，使用默认的方法来加密
     * NO_PADDING 这个参数是略去加密字符串最后的”=”
     * NO_WRAP 这个参数意思是略去所有的换行符（设置后CRLF就没用了）
     * URL_SAFE 这个参数意思是加密时不使用对URL和文件名有特殊意义的字符来作为加密字符，具体就是以-和_取代+和/
     */
    fun toBase64(str: String): String? {
        return Base64.encodeToString(str.toByteArray(), Base64.NO_WRAP)
    }

    /**
     * 将base64字符串根据key转换成HmacSha1格式
     */
    @Throws(Exception::class)
    open fun HmacSHA1Encrypt(
        encryptText: String,
        encryptKey: String
    ): ByteArray? {
        val data = encryptKey.toByteArray(charset("UTF-8"))
        // 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
        val secretKey: SecretKey = SecretKeySpec(data, "HmacSHA1")
        // 生成一个指定 Mac 算法 的 Mac 对象
        val mac = Mac.getInstance("HmacSHA1")
        // 用给定密钥初始化 Mac 对象
        mac.init(secretKey)
        val text = encryptText.toByteArray(charset("UTF-8"))
        text.toList().toString().e("加密 ")
        // 完成 Mac 操作
        return mac.doFinal(text)
    }

    /**
     * 将字节数组转换成MD5格式字符串
     */
    fun ByteArray.md5(): String {
        try {
            val bmd5 = MessageDigest.getInstance("MD5")
            bmd5.update(this)
            var i: Int
            val buf = StringBuffer()
            val b = bmd5.digest()
            for (offset in b.indices) {
                i = b[offset].toInt()
                if (i < 0) i += 256
                if (i < 16) buf.append("0")
                buf.append(Integer.toHexString(i))
            }
            return buf.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     * 获取设备Id
     */
    @SuppressLint("HardwareIds")
    fun getAndroidId(): String {
        return android.provider.Settings.Secure.getString(
            AppGlobal.getContext().contentResolver,
            android.provider.Settings.Secure.ANDROID_ID
        )
    }

}