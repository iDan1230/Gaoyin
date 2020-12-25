package com.idan.frame.model


data class PageInfo<T>(val page: Long, val total: Long, val records: Long, val rows: ArrayList<T>)
