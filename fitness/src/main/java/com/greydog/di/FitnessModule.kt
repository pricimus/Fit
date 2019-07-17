package com.greydog.di

import com.greydog.fitness.LoginViewModel
import com.greydog.fitness.StepsViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val fitnessModule = module {

    viewModel { LoginViewModel(get()) }
    viewModel { StepsViewModel(get()) }
}