package com.idan.frame.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<DB : ViewDataBinding, D>(var datas: MutableList<D>) :
    RecyclerView.Adapter<ViewHolder<D>>() {

    override fun getItemCount(): Int = datas.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<D> {
        val mDb = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layoutRes(),
            parent,
            false
        )
        return ViewHolder(mDb)
    }

    override fun onBindViewHolder(holder: ViewHolder<D>, position: Int) {
        val db = DataBindingUtil.getBinding<DB>(holder.itemView)
        db?.let {
            bindItem(it, datas[position])
        }
//        bindItem(holder.itemView,datas[position])
    }

    abstract fun layoutRes(): Int

    abstract fun bindItem(mDB: DB, item: D)

}

class ViewHolder<D>(val mBind: ViewDataBinding) : RecyclerView.ViewHolder(mBind.root) {
//    fun bind(item: D, block: (T, D) -> Unit) {
//        block.invoke(mBind, item)
//    }
}

