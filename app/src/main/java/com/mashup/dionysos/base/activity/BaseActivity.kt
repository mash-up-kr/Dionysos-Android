package com.mashup.dionysos.base.activity

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.mashup.dionysos.api.MogakgongApi
import com.mashup.dionysos.api.MogakgongRetrofit


abstract class BaseActivity<B : ViewDataBinding>(private val layoutId: Int) : AppCompatActivity() {
    lateinit var binding: B

    companion object {
        private const val sharedPrefFile = "app_preferences"
        val basePath = "/data/data/com.mashup.dionysos/files/"
        const val jwt = "jwt"
    }

    lateinit var mPreferences: SharedPreferences
    lateinit var preferencesEditor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE)
    }
}
