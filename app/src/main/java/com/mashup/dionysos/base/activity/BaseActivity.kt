package com.mashup.dionysos.base.activity

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.mashup.dionysos.api.MogakgongRetrofit
import com.mashup.dionysos.ui.login.LoginActivity


abstract class BaseActivity<B : ViewDataBinding>(private val layoutId: Int) : AppCompatActivity() {
    lateinit var binding: B

    companion object {
        private const val sharedPrefFile = "app_preferences"
        const val jwt = "jwt"
    }
    val repository = MogakgongRetrofit.getService()

    lateinit var mPreferences: SharedPreferences
    lateinit var preferencesEditor:SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

    }
}
