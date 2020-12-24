package com.idan.frame.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * @Creator yangzhidan
 * @Date    2020/12/21/0021
 * @Remark  activity基类
 */
abstract class BaseActivity<DB : ViewDataBinding> : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mDb = DataBindingUtil.setContentView<DB>(this, layoutRes())
        onBindData(mDb)
        initView()
        initData()
    }

    abstract fun layoutRes(): Int

    abstract fun onBindData(mDB:DB)

    open fun initView(){}

    open fun initData(){}
}