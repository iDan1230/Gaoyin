package com.idan.home.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.alibaba.android.arouter.facade.annotation.Route
import com.idan.frame.base.BaseActivity
import com.idan.home.R
import com.idan.home.databinding.ActivityHomeBinding
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

@Route(path = "/main/home")
class HomeActivity : BaseActivity<ActivityHomeBinding,HomeViewModel>() {

    override val mVM: HomeViewModel by viewModel()

    override fun layoutRes(): Int = R.layout.activity_home

    override fun initView() {
        super.initView()
//        val navHoseFragment =
//            supportFragmentManager.findFragmentById(R.id.navigate_host_fragment) as NavHostFragment
//
//        mDb.bnv.setupWithNavController(navHoseFragment.navController)

        mDb.bnv.setupWithNavController(findNavController(R.id.navigate_host_fragment))
    }
}