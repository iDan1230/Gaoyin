package com.idan.home.ui.album

import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import com.idan.frame.base.BaseFragment
import com.idan.home.BR
import com.idan.home.R
import com.idan.home.databinding.FragmentAlbumListBinding
import com.idan.home.logic.model.AlbumsVO
import com.idan.home.ui.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * @Author yangzhidan
 * @Date   2020/12/28
 * @Remark 专辑列表
 */
class AlbumListFragment : BaseFragment<FragmentAlbumListBinding>() {

    private val mVM: MainViewModel by sharedViewModel()

    var title = ObservableField("这是title")

    override fun layoutRes(): Int = R.layout.fragment_album_list

    override fun onBindData(mDB: FragmentAlbumListBinding) {
        mDB.title = title.get()
    }

    override fun initView() {
        super.initView()
        mDb.btn.setOnClickListener {
            title.set("变一下")
            title.notifyPropertyChanged(BR.title)
        }
    }


}