package com.idan.home.ui.fragment

import android.os.Bundle
import androidx.databinding.ObservableField
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.idan.frame.ID
import com.idan.frame.ITEM
import com.idan.frame.base.BaseFragment
import com.idan.frame.base.BasePagingAdapter
import com.idan.frame.ktx.e
import com.idan.home.R
import com.idan.home.databinding.FragmentAlbumListBinding
import com.idan.home.databinding.HomeItemAlbumsHBinding
import com.idan.home.logic.model.AlbumsVO
import com.idan.home.ui.main.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * @Author yangzhidan
 * @Date   2020/12/28
 * @Remark 专辑列表
 */
class AlbumListFragment : BaseFragment<FragmentAlbumListBinding>() {

    private val mVM: MainViewModel by sharedViewModel()

    var title = ObservableField("专辑列表")

    override fun layoutRes(): Int = R.layout.fragment_album_list

    override fun onBindData(mDB: FragmentAlbumListBinding) {
        mDB.title = title.get()
    }

    override fun initView() {
        super.initView()
        mDb.recycler.apply {
            adapter = pagingAdapter.createFooter()
        }
        var item = arguments?.getParcelable<AlbumsVO>(ITEM)
        var id = arguments?.getInt(ID)
        lifecycleScope.launch {
            mVM.queryAlbumsList(id ?: 0, tagName = item?.album_title).collectLatest {
                pagingAdapter.submitData(it)
            }
        }
    }

    private var pagingAdapter =
        BasePagingAdapter<HomeItemAlbumsHBinding, AlbumsVO>(R.layout.home_item_albums_h) { db, item, _, _ ->
            db.item = item
            item.toString().e("Item =========> ")
        }.apply {
            onClickItem = {
                position,item->
                findNavController().navigate(R.id.to_albuminfo, Bundle().apply {
                    putParcelable(ITEM,item)
                })
            }
        }
}