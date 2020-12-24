package com.idan.login.ui.login

import com.alibaba.android.arouter.facade.annotation.Route
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
    }
}