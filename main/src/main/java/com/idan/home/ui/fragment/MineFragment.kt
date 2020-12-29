package com.idan.home.ui.fragment

import com.idan.frame.base.BaseFragment
import com.idan.home.R
import com.idan.home.databinding.FragmentHomeBinding
import com.idan.home.databinding.FragmentMineBinding
import com.idan.home.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MineFragment : BaseFragment<FragmentMineBinding>() {

    private val mVM by sharedViewModel<HomeViewModel>()

    override fun onBindData(mDB: FragmentMineBinding) {
    }

    override fun layoutRes(): Int = R.layout.fragment_mine

}