package com.mashup.dionysos.api

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MogakgongRetrofit(val applicationContext: Context) {
    private val baseUrl = "http://13.125.51.10:8080/"
    private val sharedPreferences by lazy {
        applicationContext.getSharedPreferences(
            "app_preferences",
            AppCompatActivity.MODE_PRIVATE
        )
    }
    private var authToken: String? = sharedPreferences.getString("jwt", null)

    fun getService(): MogakgongApi = retrofit.create(
        MogakgongApi::class.java
    )

    private var client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
        .addInterceptor { chain ->
            val newRequest = chain.request().newBuilder().apply {
                authToken?.let {
                    addHeader("Authorization", it)
                }
            }.build()
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

