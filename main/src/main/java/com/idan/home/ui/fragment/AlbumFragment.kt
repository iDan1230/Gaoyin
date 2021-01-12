package com.idan.home.ui.fragment

import com.idan.frame.ITEM
import com.idan.frame.base.BaseFragment
import com.idan.frame.ktx.e
import com.idan.home.R
import com.idan.home.databinding.FragmentAlbumBinding
import com.idan.home.logic.model.AlbumsVO

/**
 * @Creator yangzhidan
 * @Date    2021/1/12/0012
 * @Remark  专辑详情
 */
class AlbumFragment : BaseFragment<FragmentAlbumBinding>() {

    override fun onBindData(mDB: FragmentAlbumBinding) {

    }

    override fun layoutRes(): Int =R.layout.fragment_album

    override fun initView() {
        super.initView()
        arguments?.getParcelable<AlbumsVO>(ITEM).e("默认页的参数--> ")
//        mDb.bnv.setupWithNavController(findNavController(R.id.navigate_host_fragment))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        "onDestroyView".e()
    }

}