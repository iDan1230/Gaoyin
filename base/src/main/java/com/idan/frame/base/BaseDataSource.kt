package com.idan.frame.base

import androidx.paging.PagingSource
import com.idan.frame.ktx.e
import com.idan.frame.model.PageInfo

/**
 * @Author yangzhidan
 * @Date   2020/12/25
 * @Remark padingSource封装
 */
class BaseDataSource<D : Any>(val queryPage: suspend (Int) -> PageInfo<D>) :
    PagingSource<Int, D>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, D> {
        return try {
            val currentPage = params.key ?: 1
            val info = queryPage.invoke(currentPage)
            info.toString().e("PAGING_RESULT ===> ")
            val nextPage = if (info.total_page > currentPage) {
                currentPage + 1
            } else {
                null
            }
            if (info.rows.isNotEmpty()) {
                LoadResult.Page(info.rows, prevKey = null, nextKey = nextPage)
            } else {
                LoadResult.Error(Throwable("没有更所数据"))
            }
        } catch (e: Throwable) {
            "11113$e".e()
            LoadResult.Error(e)
        }
    }
}