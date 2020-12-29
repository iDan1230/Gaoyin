package com.idan.home.ui.fragment

import com.idan.frame.base.BaseFragment
import com.idan.home.R
import com.idan.home.databinding.FragmentFindBinding
import com.idan.home.databinding.FragmentHomeBinding
import com.idan.home.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FindFragment : BaseFragment<FragmentFindBinding>() {

    private val mVM by sharedViewModel<HomeViewModel>()

    override fun onBindData(mDB: FragmentFindBinding) {
    }

    override fun layoutRes(): Int = R.layout.fragment_find


}