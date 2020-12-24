package com.idan.home.ui.main

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.idan.frame.ID
import com.idan.frame.TITLE
import com.idan.frame.base.BaseActivity
import com.idan.frame.base.BaseAdapter
import com.idan.frame.ktx.e
import com.idan.home.R
import com.idan.home.databinding.ActivityMainBinding
import com.idan.home.databinding.HomeItemTestListLayoutBinding
import com.idan.home.logic.model.Test
import kotlinx.android.synthetic.main.activity_main.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

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

        mVM.tests.observe(this, Observer {
            datas.addAll(it)
            mDb.root.recycler.adapter?.notifyDataSetChanged()
        })

        mDb.root.recycler.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = object :BaseAdapter<Test>(datas){
                override fun itemLayoutRes(): Int = R.layout.home_item_test_list_layout

                override fun bindItem(view: View, item: Test) {
                   val iDB = DataBindingUtil.getBinding<HomeItemTestListLayoutBinding>(view)
                    iDB?.item = item
                }
            }
        }
    }
}