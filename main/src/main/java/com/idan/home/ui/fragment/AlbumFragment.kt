package com.idan.home.ui.fragment

import com.idan.frame.base.BaseFragment
import com.idan.home.R
import com.idan.home.databinding.FragmentAlbumBinding

class AlbumFragment : BaseFragment<FragmentAlbumBinding>() {

    override fun onBindData(mDB: FragmentAlbumBinding) {

    }

    override fun layoutRes(): Int =R.layout.fragment_album

    override fun initView() {
        super.initView()
//        mDb.bnv.setupWithNavController(findNavController(R.id.navigate_host_fragment))
    }

}