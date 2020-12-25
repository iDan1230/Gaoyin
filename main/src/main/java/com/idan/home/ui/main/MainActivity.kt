package com.idan.home.ui.main

import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.idan.frame.ID
import com.idan.frame.TITLE
import com.idan.frame.base.BaseActivity
import com.idan.frame.base.BasePagingAdapter
import com.idan.frame.ktx.e
import com.idan.home.R
import com.idan.home.databinding.ActivityMainBinding
import com.idan.home.databinding.HomeItemTestBinding
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

    val datas = mutableListOf<Test>()

    override val mVM: MainViewModel by viewModel()

    override fun layoutRes(): Int = R.layout.activity_main

    override fun initView() {
        mDb.vm = mVM
        "$title ------ $id".e()

        mDb.root.recycler.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = pagingAdapter
            lifecycleScope.launch {
                mVM.loadData(199916).collectLatest {
                    pagingAdapter.submitData(it)
                }
            }
        }
        mDb.title.setTitle("主页面")
    }

    private var pagingAdapter =
        BasePagingAdapter<HomeItemTestBinding, Message>(R.layout.home_item_test) { db, item, position, adapter ->
            db.item = item
            db.root.setOnClickListener {
                item.compel = 1
                adapter.notifyItemChanged(position)
            }
        }
}