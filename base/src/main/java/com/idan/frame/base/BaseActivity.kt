package com.idan.frame.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.launcher.ARouter
import com.idan.frame.model.Failed
import com.idan.frame.model.Finish
import com.idan.frame.model.LoadState
import com.idan.frame.model.Success
import com.idan.frame.widget.dialog.Loading

/**
 * @Creator yangzhidan
 * @Date    2020/12/21/0021
 * @Remark  activity基类
 */
abstract class BaseActivity<DB : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {

    abstract val mVM: VM

    private val loading: Loading by lazy { Loading() }

    val mDb: DB by lazy { DataBindingUtil.setContentView<DB>(this, layoutRes()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //必须执行inject才能获取通过Arouter传递的参数
        ARouter.getInstance().inject(this)
        mDb.lifecycleOwner = this
        initView()
        initData()
        mVM.state.observe(this, Observer {
            when (it) {
                is LoadState -> showLoading()
                is Success -> closeLoading()
                is Failed -> failedLoading(it.fail)
                is Finish -> finish()
            }
        })
    }

    abstract fun layoutRes(): Int

    open fun initView() {}

    open fun initData() {}

    open fun showLoading() {
        if (loading.isAdded) {
            supportFragmentManager.beginTransaction().remove(loading)
        } else {
            loading.show(supportFragmentManager)
        }
    }

    open fun closeLoading() {
        if (loading.isVisible) {
            loading.dismissAllowingStateLoss()
        }
    }

    open fun failedLoading(fail: String) {
        if (!isFinishing) {
            loading.setMessage(fail)
        }
    }
}