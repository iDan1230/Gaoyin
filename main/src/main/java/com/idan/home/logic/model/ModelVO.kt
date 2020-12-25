package com.idan.home.logic.model

data class Test(var title:String)


data class PageInfo<T>(val page: Long, val total: Long, val records: Long, val rows: ArrayList<T>)


data class Message(val id: Int, var title: String, val typeName: String, val content: String,val createDate:String)