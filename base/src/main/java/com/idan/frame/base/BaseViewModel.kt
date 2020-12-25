package com.idan.frame.base

import android.graphics.pdf.PdfDocument
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.idan.frame.http.handleExcption
import com.idan.frame.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * @Author yangzhidan
 * @Date   2020/12/24
 * @Remark VM基类
 */
open class BaseViewModel : ViewModel() {

    open val state = MutableLiveData<State>()

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
}