package com.mashup.dionysos.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.mashup.dionysos.api.MogakgongRetrofit

abstract class BaseFragment<B : ViewDataBinding>(private val layoutId: Int) : Fragment() {
    lateinit var binding: B
    val basePath = "/data/data/com.mashup.dionysos/files/"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            layoutId,
            container,
            false
        )
        binding.lifecycleOwner = this
        return binding.root
    }
}