package com.idan.frame.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * @Author yangzhidan
 * @Date   2020/12/25
 * @Remark 列表适配器基类
 * @param DB:根据布局资源生成的
 */
abstract class BaseAdapter<DB : ViewDataBinding, D>(var datas: MutableList<D>) :
    RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int = datas.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val mDb = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layoutRes(),
            parent,
            false
        )
        return ViewHolder(mDb)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val db = DataBindingUtil.getBinding<DB>(holder.itemView)
        db?.let {
            bindItem(it, datas[position])
        }
    }

    abstract fun layoutRes(): Int

    abstract fun bindItem(mDB: DB, item: D)

}

class ViewHolder(mDB: ViewDataBinding) : RecyclerView.ViewHolder(mDB.root)

