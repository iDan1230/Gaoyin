package com.idan.home.ui.main

import com.idan.frame.base.BaseViewModel
import com.idan.frame.ktx.show
import com.idan.home.logic.http.MainRepository

class MainViewModel(private val mainRepository: MainRepository) : BaseViewModel() {

    fun clear(){
        "clear".show()
    }
    fun loadData() = flowData {
        mainRepository.queryMessage()
    }
}