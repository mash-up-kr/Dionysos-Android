package com.mashup.dionysos.di

import com.mashup.dionysos.api.MogakgongRetrofit
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val apiModule = module {
    single {
        MogakgongRetrofit(applicationContext = androidApplication()).getService()
    }
}
