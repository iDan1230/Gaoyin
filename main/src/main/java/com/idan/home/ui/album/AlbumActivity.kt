package com.idan.home.ui.album

import android.annotation.SuppressLint
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.idan.frame.base.BaseActivity
import com.idan.home.R
import com.idan.home.databinding.ActivityAlbumBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @Author yangzhidan
 * @Date   2020/12/30
 * @Remark 专辑壳子
 */
class AlbumActivity : BaseActivity<ActivityAlbumBinding, AlbumViewModel>() {

    override val mVM: AlbumViewModel by viewModel()

    override fun layoutRes(): Int = R.layout.activity_album

    override fun initView() {
        super.initView()
//        val navController = findNavController(R.id.navigate_album)
//        val navInflater = navController.navInflater
//        val navGraph = navInflater.inflate(R.navigation.nav_album)
//        navGraph.startDestination = R.id.albumListFragment
//        navController.setGraph(navGraph, null)

        findNavController(R.id.navigate_album).apply {
            graph = navInflater.inflate(R.navigation.nav_album).let {
                it.startDestination = R.id.albumListFragment
                it
            }
        }
    }
}