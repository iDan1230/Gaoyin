package com.idan.home.ui.main

import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.tabs.TabLayout
import com.idan.frame.ID
import com.idan.frame.TITLE
import com.idan.frame.base.BaseActivity
import com.idan.frame.base.BaseAdapter
import com.idan.frame.base.BasePagingAdapter
import com.idan.frame.base.FooterAdapter
import com.idan.frame.ktx.e
import com.idan.frame.ktx.show
import com.idan.home.R
import com.idan.home.databinding.ActivityMainBinding
import com.idan.home.databinding.HomeItemTestBinding
import com.idan.home.logic.model.Albums
import com.idan.home.logic.model.Category
import com.idan.home.logic.model.Message
import com.idan.home.logic.model.Test
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

    val datas = mutableListOf<Category>()

    var category: Category? = null

    override val mVM: MainViewModel by viewModel()

    override fun layoutRes(): Int = R.layout.activity_main

    override fun initView() {
        mDb.vm = mVM
        "$title ------ $id".e()

        mVM.categorys.observe(this, Observer {
            it.toString().e()
            mDb.tab.removeAllTabs()
            it.forEach {
                mDb.tab.addTab(mDb.tab.newTab().setText(it.category_name))
            }
        })

        mDb.tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                category = datas[tab!!.position]
//                pagingAdapter.refresh()
            }
        })
//        mDb.recycler.adapter = mainAdapter

        mDb.root.recycler.apply {

            adapter = pagingAdapter.createFooter()

            lifecycleScope.launch {
                category?.let {
                    mVM.loadCategoryDatas(category!!).collectLatest {
                        pagingAdapter.submitData(it)
                    }
                }
            }
        }
        //刷新
//        pagingAdapter.refresh()
        //重试
//        pagingAdapter.retry()
//        mDb.title.setTitle("主页面")
    }

    override fun initData() {
        super.initData()
        mVM.queryCategorys()
    }

    private var pagingAdapter =
        BasePagingAdapter<HomeItemTestBinding, Albums>(R.layout.home_item_test) { db, item, position, adapter ->
            db.item = item
            db.root.setOnClickListener {
                item.is_finished = 1
                adapter.notifyItemChanged(position)
            }
        }
}