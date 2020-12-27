package com.idan.frame.model

import com.google.gson.annotations.SerializedName


data class PageInfo<T>(val page: Long,
                       val total_page: Long,
                       val total_count: Long,
                       @SerializedName("rows", alternate = ["albums", "tracks"])
                       val rows: ArrayList<T>)
