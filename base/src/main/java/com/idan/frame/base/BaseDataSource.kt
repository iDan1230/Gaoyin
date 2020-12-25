package com.idan.frame.base

import androidx.paging.PagingSource
import com.idan.frame.ktx.e

/**
 * @Author yangzhidan
 * @Date   2020/12/25
 * @Remark padingSource封装
 */
class BaseDataSource<D : Any>(val queryPage: suspend (Int) -> List<D>) : PagingSource<Int, D>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, D> {
        return try {
            val currentPage = params.key ?: 1
            "11111".e()
            val tests = queryPage.invoke(currentPage)
            "11112".e()
            val nextPage = if (currentPage + 1 < 5) {
                currentPage + 1
            } else {
                null
            }
            if (tests.isNotEmpty()) {
                LoadResult.Page(tests, prevKey = null, nextKey = nextPage)
            } else {
                LoadResult.Error(Throwable("没有更所数据"))
            }
        } catch (e: Throwable) {
            "11113$e".e()
            LoadResult.Error(e)
        }
    }
}