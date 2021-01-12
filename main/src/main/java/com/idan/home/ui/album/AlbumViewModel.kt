package com.idan.home.ui.album

import com.idan.frame.base.BaseViewModel
import com.idan.home.logic.http.MainRepository
import com.idan.home.logic.model.AlbumsVO

class AlbumViewModel(private val mainRepository: MainRepository) : BaseViewModel() {


    fun queryAlbums(categoryId: Int, tagName: String) = flowData {
        val map = mutableMapOf(
            "category_id" to categoryId.toString(),
            "calc_dimension" to "1",
            "page" to "1",
            "count" to "20",
            "contains_paid" to "false",
            "tag_name" to tagName
        )
        mainRepository.queryAlbumsList(createParams(map))
    }
}