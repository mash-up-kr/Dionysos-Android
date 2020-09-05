package com.mashup.dionysos.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mashup.dionysos.BR
import com.mashup.dionysos.R
import com.mashup.dionysos.databinding.BottomSheetLayoutBinding


class BottomSheetDialog : BottomSheetDialogFragment() {

    lateinit var timeViewModel: TimeViewModel
    lateinit var binding: BottomSheetLayoutBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.bottom_sheet_layout, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModelFactory =
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        timeViewModel = ViewModelProvider(activity!!, viewModelFactory).get(TimeViewModel::class.java)
        binding.setVariable(BR.timeVM, timeViewModel)
        timeViewModel.timeLaps.observe(this, Observer { it ->
            dismiss()
        })


    }

    override fun getTheme(): Int =
        R.style.BottomSheetDialogTheme


    companion object {
        val instance: BottomSheetDialog
            get() = BottomSheetDialog()
    }
}
