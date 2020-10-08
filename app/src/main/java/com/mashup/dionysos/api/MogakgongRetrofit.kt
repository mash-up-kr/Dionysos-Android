package com.mashup.dionysos.api

import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MogakgongRetrofit {
    private val baseUrl = "http://13.125.51.10:8080/"
    private var authToken: String? = null

    fun getService(): MogakgongApi = retrofit.create(
        MogakgongApi::class.java
    )
    constructor (authToken: String? = "") {
        Log.e("authToken",authToken?:"null")
        this.authToken = authToken
    }

    private var client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", authToken)
                .build()
            chain.proceed(newRequest)
        }.build()

    private val retrofit =
        Retrofit
            .Builder()
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl(baseUrl)
            .build()

}

