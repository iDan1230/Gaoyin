package com.idan.frame.base

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.idan.frame.ktx.e
import com.idan.frame.ktx.show

/**
 * @Author yangzhidan
 * @Date   2020/12/25
 * @Remark 列表适配器基类
 * @param DB:根据布局资源生成的
 */
class BasePagingAdapter<DB : ViewDataBinding, D : Any>(
    @LayoutRes private val itemRes: Int,
    private val onAttach: ((D?) -> Int)? = null,
    val onBindItem: (DB, D, Int, adater: BasePagingAdapter<*, *>) -> Unit
) :
    PagingDataAdapter<D, PagingHolder>(object : DiffUtil.ItemCallback<D>() {
        override fun areItemsTheSame(oldItem: D, newItem: D): Boolean {
            return false
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: D, newItem: D): Boolean {
            return newItem === oldItem
        }
    }) {


    /**
     * 表格布局时，可定制样式
     */
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        onAttach?.let {
            val grid = recyclerView.layoutManager
            if (grid is GridLayoutManager) {
                grid.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
//                    override fun getSpanSize(position: Int): Int {
//                        return 1
//                    }

                    override fun getSpanSize(position: Int): Int {
                        return if (position < itemCount) {
                            onAttach.invoke(getItem(position))
                        } else {
                            1
                        }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingHolder {
        val mDb = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            itemRes,
            parent,
            false
        )
        return PagingHolder(mDb)
    }

    override fun onBindViewHolder(holder: PagingHolder, position: Int) {
        val db = DataBindingUtil.getBinding<DB>(holder.itemView)
        db?.let { db ->
            onBindItem(db, getItem(position)!!, position, this)
        }
    }

    fun createFooter(): ConcatAdapter {
        addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                //加载中
            } else {
                //其他状态
                val error = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }

                error?.let {
                    it.error.message.show()
                }
            }
        }

        return withLoadStateFooter(FooterAdapter(this))
    }

}

class PagingHolder(mDB: ViewDataBinding) : RecyclerView.ViewHolder(mDB.root)

