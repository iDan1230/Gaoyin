package com.idan.login.logic.di

import com.idan.frame.http.create
import com.idan.login.logic.http.LoginRepository
import com.idan.login.logic.http.LoginService
import com.idan.login.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    viewModel { LoginViewModel(get()) }
    factory { LoginRepository(get()) }
    single{create<LoginService>()}
}