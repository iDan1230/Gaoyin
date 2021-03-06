package com.idan.home.ui.main

import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.tabs.TabLayout
import com.idan.frame.ID
import com.idan.frame.TITLE
import com.idan.frame.base.BaseActivity
import com.idan.frame.base.BasePagingAdapter
import com.idan.frame.ktx.e
import com.idan.home.R
import com.idan.home.databinding.ActivityMainBinding
import com.idan.home.databinding.HomeItemAlbumsBinding
import com.idan.home.databinding.HomeItemTestBinding
import com.idan.home.logic.model.AlbumsVO
import com.idan.home.logic.model.CategoryVO
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @Author yangzhidan
 * @Date   2020/12/25
 * @Remark 主页面
 */
@Route(path = "/main/main")
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    @Autowired(name = ID)
    @JvmField
    var id: Long = 0

    @Autowired(name = TITLE)
    @JvmField
    var title: String? = null

    val datas = mutableListOf<CategoryVO>()

    var category: CategoryVO? = null

    override val mVM: MainViewModel by viewModel()

    override fun layoutRes(): Int = R.layout.activity_main

    override fun initView() {
        mDb.vm = mVM
        "$title ------ $id".e()

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
            layoutManager = GridLayoutManager(this@MainActivity, 3)
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
    }

    private var pagingAdapter =
        BasePagingAdapter<HomeItemAlbumsBinding, AlbumsVO>(
            R.layout.home_item_albums,
            { item, spanCount ->
                if (item!!.is_tag) {
                    3
                } else {
                    1
                }
            }) { db, item, position, adapter ->
            db.item = item
            db.root.setOnClickListener {
                item.is_finished = 1
                adapter.notifyItemChanged(position)
            }
        }
}