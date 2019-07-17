package com.greydog.fitapp

import android.app.Application
import com.greydog.di.fitnessModule
import com.greydog.di.networkModule
import org.koin.android.ext.android.startKoin

class FitnessApplication : Application() {
    override fun onCreate(){
        super.onCreate()

        val modules = listOf(networkModule, fitnessModule)

        startKoin(this, modules)
    }
}