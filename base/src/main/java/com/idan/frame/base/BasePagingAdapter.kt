package com.idan.frame.base

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.idan.frame.ktx.e

/**
 * @Author yangzhidan
 * @Date   2020/12/25
 * @Remark 列表适配器基类
 * @param DB:根据布局资源生成的
 */
class BasePagingAdapter<DB : ViewDataBinding, D : Any>(
    @LayoutRes private val itemRes: Int,
    val onBindItem: (DB, D,Int, adater: BasePagingAdapter<DB, D>) -> Unit
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
            onBindItem(db, getItem(position)!!,position,this)
        }
    }
}

class PagingHolder(mDB: ViewDataBinding) : RecyclerView.ViewHolder(mDB.root)

