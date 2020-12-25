package com.idan.login.ui.login

import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.callback.NavigationCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.idan.frame.ID
import com.idan.frame.TITLE
import com.idan.frame.base.BaseActivity
import com.idan.login.R
import com.idan.login.databinding.ActivityLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @Creator yangzhidan
 * @Date    2020/12/21/0021
 * @Remark  登录页
 */
@Route(path = "/login/login")
class LoginActivity : BaseActivity<ActivityLoginBinding,LoginViewModel>() {

    override val mVM: LoginViewModel by viewModel()

    override fun layoutRes(): Int = R.layout.activity_login

    override fun initView() {
        mDb.vm = mVM
        mVM.userLiveData.observe(this, Observer {
            it.login_failure_msg?.apply {
                failedLoading(this)
                return@Observer
            }
            ARouter.getInstance().build("/main/main")
                .withLong(ID,10010L)
                .withString(TITLE,"TEST_TITLE")
                .navigation(this,object :NavigationCallback{
                    override fun onLost(postcard: Postcard?) {
                    }

                    override fun onFound(postcard: Postcard?) {
                    }

                    override fun onInterrupt(postcard: Postcard?) {
                    }

                    override fun onArrival(postcard: Postcard?) {
                        finish()
                    }
                })
        })
    }
}