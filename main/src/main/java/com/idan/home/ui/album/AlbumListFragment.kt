package com.idan.home.ui.album

import com.idan.frame.base.BaseFragment
import com.idan.home.R
import com.idan.home.databinding.FragmentAlbumListBinding
import com.idan.home.ui.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * @Author yangzhidan
 * @Date   2020/12/28
 * @Remark 专辑列表
 */
class AlbumListFragment : BaseFragment<FragmentAlbumListBinding>() {

    private val mVM:MainViewModel by sharedViewModel()

    override fun layoutRes(): Int = R.layout.fragment_album_list

    override fun onBindData(mDB: FragmentAlbumListBinding) {
//        mDB.category
    }


}