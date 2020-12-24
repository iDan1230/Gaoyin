package com.idan.frame.base

import androidx.lifecycle.*
import com.alibaba.fastjson.JSON
import com.idan.frame.ktx.e
import com.idan.frame.model.Failed
import com.idan.frame.model.LoadState
import com.idan.frame.model.State
import com.idan.frame.model.Success
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.lang.Exception

/**
 * @Author yangzhidan
 * @Date   2020/12/24
 * @Remark VM基类
 */
open class BaseViewModel : ViewModel() {

    open val state = MutableLiveData<State>()

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

}