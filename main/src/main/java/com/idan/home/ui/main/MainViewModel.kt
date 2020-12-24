package com.idan.home.ui.main

import androidx.lifecycle.MutableLiveData
import com.idan.frame.base.BaseViewModel
import com.idan.home.logic.http.MainRepository
import com.idan.home.logic.model.Test

class MainViewModel(private val mainRepository: MainRepository) : BaseViewModel() {

    val tests = MutableLiveData(arrayListOf<Test>())

    fun add(){
        tests.value = arrayListOf(
            Test("11111111111111"),
            Test("11111111111112"),
            Test("11111111111113"),
            Test("11111111111114"),
            Test("11111111111115"),
            Test("11111111111116"),
            Test("11111111111116"),
            Test("11111111111118"),
            Test("11111111111117")
        )
    }
}