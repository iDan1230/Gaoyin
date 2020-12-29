package com.idan.home.logic.di

import com.idan.frame.http.create
import com.idan.home.ui.main.MainViewModel
import com.idan.home.logic.http.MainRepository
import com.idan.home.logic.http.MainService
import com.idan.home.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    factory { MainRepository(get()) }
    single{create<MainService>()}
}