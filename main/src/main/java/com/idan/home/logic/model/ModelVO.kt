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

data class Category(
    val id: Long,
    val kind: String,
    val category_name: String,
    val cover_url_small: String,
    val cover_url_middle: String,
    val cover_url_large: String,
    var order_num: Int
)


data class Albums(
    val id: Long,
    val kind: String,
    val album_title: String,
    val album_tags: String,
    val album_intro: String,
    val cover_url_small: String,
    val cover_url_middle: String,
    val cover_url_large: String,
    var is_finished: Int
)