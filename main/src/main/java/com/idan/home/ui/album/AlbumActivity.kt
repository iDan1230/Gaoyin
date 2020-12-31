package com.idan.home.ui.album

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.navigation.NavArgument
import androidx.navigation.NavType
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.idan.frame.ID
import com.idan.frame.ITEM
import com.idan.frame.TYPE
import com.idan.frame.base.BaseActivity
import com.idan.frame.ktx.e
import com.idan.frame.ktx.show
import com.idan.home.R
import com.idan.home.databinding.ActivityAlbumBinding
import com.idan.home.logic.model.AlbumsVO
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @Author yangzhidan
 * @Date   2020/12/30
 * @Remark 专辑壳子
 */
class AlbumActivity : BaseActivity<ActivityAlbumBinding, AlbumViewModel>() {

    var type: Int = 0
    var item: AlbumsVO? = null
    override val mVM: AlbumViewModel by viewModel()

    override fun layoutRes(): Int = R.layout.activity_album

    override fun initView() {
        super.initView()

//        val bundle = intent.extras
//        bundle?.let {
            type = intent.getIntExtra(TYPE,0)
            item = intent.getParcelableExtra(ITEM)
            item.toString().e()
//        }

        findNavController(R.id.navigate_album).apply {
            setGraph(navInflater.inflate(R.navigation.nav_album).apply {
                startDestination = when (type) {
                    1 -> R.id.albumListFragment
                    else -> R.id.albumFragment
                }
            },Bundle().apply {
                putParcelable(ITEM,item)
            })
//            graph = navInflater.inflate(R.navigation.nav_album).apply {
//                startDestination = when (type) {
//                    1 -> R.id.albumListFragment
//                    else -> R.id.albumFragment
//                }
//            }
        }
    }
}