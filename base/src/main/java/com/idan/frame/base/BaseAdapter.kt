package com.idan.frame.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<D>(var datas: MutableList<D>) : RecyclerView.Adapter<ViewHolder<D>>() {

    override fun getItemCount(): Int = datas.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder< D> {
       val mDb =  DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context),itemLayoutRes(),parent,false)
        return ViewHolder(mDb)
    }

    override fun onBindViewHolder(holder: ViewHolder< D>, position: Int) {
//        holder.bind(datas[position]){
//            t,d->
//            bindData(t,d)
//        }

        bindItem(holder.itemView,datas[position])
    }

    abstract fun itemLayoutRes():Int

    abstract fun bindItem(view:View,item: D)

}

class ViewHolder<D>(val mBind: ViewDataBinding) : RecyclerView.ViewHolder(mBind.root) {
//    fun bind(item: D, block: (T, D) -> Unit) {
//        block.invoke(mBind, item)
//    }
}

