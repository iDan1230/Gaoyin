package com.idan.home.ui.main

import androidx.lifecycle.MutableLiveData
import com.idan.frame.APP_KEY
import com.idan.frame.APP_SECRET
import com.idan.frame.base.BaseViewModel
import com.idan.frame.ktx.e
import com.idan.frame.ktx.show
import com.idan.home.logic.http.MainRepository
import com.idan.home.logic.model.Category

class MainViewModel(private val mainRepository: MainRepository) : BaseViewModel() {

    val categorys = MutableLiveData<MutableList<Category>>()

    fun clear() {
        "clear".show()
    }

    fun loadData(userId: Long) = flowData {
        mainRepository.queryMessage(userId, it)
    }


    fun queryCategorys() {
        launch {
            val map = mutableMapOf(
                "app_key" to APP_KEY,
                "client_os_type" to "2",
                "nonce" to getRandomStr(32),
                "server_api_version" to "1.0.0"
            )
            map["sig"] = createSig(map, APP_SECRET) ?: ""
//            map["sig"] = createSig(map, "4d8e605fa7ed546c4bcb33dee1381179z0hh5l9A") ?: ""
            map.toString().e("类型请求参数")
            categorys.value = mainRepository.queryCategories(map)
            categorys.value.toString().e()
        }
    }


    fun loadCategoryDatas(category: Category) = flowData {
        val map = mutableMapOf(
            "app_key" to APP_KEY,
            "client_os_type" to "2",
            "nonce" to getRandomStr(32),
            "server_api_version" to "1.0.0",
            "category_id" to category.id.toString(),
            "calc_dimension" to "0"

        )
        map["sig"] = createSig(map, APP_SECRET) ?: ""
        mainRepository.queryAlbumsList(map)
    }

}