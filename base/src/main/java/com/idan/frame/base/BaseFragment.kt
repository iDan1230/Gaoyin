package com.idan.frame.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * @Author yangzhidan
 * @Date   2020/12/24
 * @Remark fragment基类
 */
abstract class BaseFragment<DB : ViewDataBinding> : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var mDb = DataBindingUtil.setContentView<DB>(activity as AppCompatActivity, layoutRes())
        onBindData(mDb)
        return mDb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }
    abstract fun onBindData(mDB:DB)

    open fun initView() {}

    open fun initData() {}

    abstract fun layoutRes(): Int
}