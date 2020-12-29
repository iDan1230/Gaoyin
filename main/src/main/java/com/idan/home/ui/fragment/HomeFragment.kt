package com.idan.home.ui.fragment

import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.tabs.TabLayout
import com.idan.frame.base.BaseFragment
import com.idan.frame.base.BasePagingAdapter
import com.idan.frame.ktx.show
import com.idan.home.R
import com.idan.home.databinding.FragmentHomeBinding
import com.idan.home.databinding.HomeItemAlbumsBinding
import com.idan.home.logic.model.AlbumsVO
import com.idan.home.logic.model.CategoryVO
import com.idan.home.ui.home.HomeViewModel
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    var category: CategoryVO? = null

    private val mVM by sharedViewModel<HomeViewModel>()

    override fun onBindData(mDB: FragmentHomeBinding) {

    }

    override fun layoutRes(): Int = R.layout.fragment_home

    override fun initView() {
        super.initView()
        mVM.categorys.observe(this, Observer {
            mDb.tab.removeAllTabs()
            it.forEach { category ->
                mDb.tab.addTab(mDb.tab.newTab().setText(category.category_name))
            }
        })

        mVM.tags.observe(this, Observer {
            initLoad()
        })

        mDb.tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                category = mVM.categorys.value?.get(tab?.position!!)
                category?.id?.let { mVM.queryTags(it) }
//                initLoad()
            }
        })
        mDb.root.recycler.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = pagingAdapter.createFooter()
        }
    }

    override fun initData() {
        super.initData()
        mVM.queryCategorys()
    }


    /**
     * 加载列表数据
     * 重点：paging开始加载之后 category,已被缓存不受外部影响，所以tabLayout切换之后需要重新调用一次此方法（initLoad）
     */
    fun initLoad() {
        lifecycleScope.launch {
            mVM.queryAlbumsList(category!!.id, 6).collectLatest {
                pagingAdapter.submitData(it)
            }
        }
//        mVM.queryTags(category!!.id)
//        lifecycleScope.launch {
//            mVM.loadCategoryDatas(category!!).collectLatest {
//                pagingAdapter.submitData(it)
//            }
//        }
    }

    private var pagingAdapter =
        BasePagingAdapter<HomeItemAlbumsBinding, AlbumsVO>(
            R.layout.home_item_albums,
            { item, spanCount ->
                if (item!!.is_tag) {
                    spanCount
                } else {
                    1
                }
            }) { db, item, position, adapter ->
            db.item = item
            db.root.setOnClickListener {
                if (!item.is_tag) {
                    item.is_finished = 1
                    adapter.notifyItemChanged(position)
                } else {
                    "更多".show()
                }
            }
        }

}