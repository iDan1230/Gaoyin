package com.idan.home.ui.main

import androidx.lifecycle.MutableLiveData
import com.idan.frame.APP_KEY
import com.idan.frame.APP_SECRET
import com.idan.frame.base.BaseViewModel
import com.idan.frame.ktx.e
import com.idan.frame.ktx.show
import com.idan.home.logic.http.MainRepository
import com.idan.home.logic.model.AlbumsVO
import com.idan.home.logic.model.CategoryVO
import com.idan.home.logic.model.TagVO

class MainViewModel(private val mainRepository: MainRepository) : BaseViewModel() {

    val categorys = MutableLiveData<MutableList<CategoryVO>>()

    val tags = MutableLiveData<MutableList<TagVO>>()

    val tagsToCategory = mutableMapOf<String, MutableList<TagVO>?>()

    fun clear() {
        "clear".show()
    }

    fun loadData(userId: Long) = flowData {
        mainRepository.queryMessage(userId, it)
    }

    /**
     * 获取分类数据
     */
    fun queryCategorys() {
        launch {
            val map = mutableMapOf(
                "app_key" to APP_KEY,
                "client_os_type" to "2",
                "nonce" to getRandomStr(32),
                "server_api_version" to "1.0.0"
            )
            map["sig"] = createSig(map, APP_SECRET) ?: ""
            categorys.value = mainRepository.queryCategories(map)
            categorys.value?.forEach {
                it.toString().e("分类 ===> ")
            }
        }
    }

    fun queryTags(categoryId: Int) {
        tagsToCategory[categoryId.toString()]?.let {
            tags.value = it
            return
        }
        launch {
            tags.value = mainRepository.queryTags(
                createParams(
                    mapOf(
                        "category_id" to categoryId.toString(),
                        "type" to "0"
                    )
                )
            )
            tagsToCategory[categoryId.toString()] = tags.value
        }
    }

    fun queryAlbumsList(categoryId: Int, pageSize: Int) = flowData {
        val map = mutableMapOf(
            "category_id" to categoryId.toString(),
            "calc_dimension" to "1",
            "page" to "1",
            "count" to pageSize.toString(),
            "contains_paid" to "false"
        )
        tags.value?.apply {
            map["tag_name"] = get(it).tag_name
        }

        val result = mainRepository.queryAlbumsList(createParams(map))
        if (pageSize == 6) {
            result.rows.add(0, AlbumsVO(categoryId.toLong(), "", map["tag_name"] ?: "", "", "", "", "", "", 0,true))
        }
        result
    }

    /**
     * 根据分类id查询专辑列表
     */
    fun loadCategoryDatas(category: CategoryVO) = flowData {
        val map = mutableMapOf(
            "category_id" to category.id.toString(),
            "calc_dimension" to "1",
            "page" to "1",
            "count" to "20",
            "contains_paid" to "false"
        )
        category.id.toString().e("分类ID: ")
        mainRepository.queryAlbumsList(createParams(map))

    }

}