package com.idan.home.logic.model

data class Test(var title: String)

data class Message(
    val id: Int,
    var title: String,
    val typeName: String,
    val content: String,
    val createDate: String,
    var compel: Int
)

data class CategoryVO(
    val id: Int,
    val kind: String,
    val category_name: String,
    val cover_url_small: String,
    val cover_url_middle: String,
    val cover_url_large: String,
    var order_num: Int
)

data class TagVO(val tag_name: String, val kind: String)


data class AlbumsVO(
    val id: Long,
    val kind: String,
    val album_title: String,
    val album_tags: String,
    val album_intro: String,
    val cover_url_small: String,
    val cover_url_middle: String,
    val cover_url_large: String,
    var is_finished: Int,
    //自定义用来判断是否是tag
    var is_tag: Boolean
)