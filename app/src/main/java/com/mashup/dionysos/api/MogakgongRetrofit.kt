package com.mashup.dionysos.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object MogakgongRetrofit{
    private val baseUrl = "http://3.34.129.246:8080/"
    fun getService(): MogakgongApi = retrofit.create(
        MogakgongApi::class.java)

    private val retrofit =
        Retrofit
            .Builder()
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
}