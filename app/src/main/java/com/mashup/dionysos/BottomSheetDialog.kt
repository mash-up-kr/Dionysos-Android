package com.mashup.dionysos

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mashup.dionysos.databinding.BottomSheetLayoutBinding
import com.mashup.dionysos.viewmodel.TimeViewModel
import kotlinx.android.synthetic.main.bottom_sheet_layout.*


class BottomSheetDialog : BottomSheetDialogFragment() {
    lateinit var timeViewModel: TimeViewModel

     lateinit var binding: BottomSheetLayoutBinding

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btnYeah.setOnClickListener {
            Log.e("BottomSheetDialog","Button 1 clicked")
            dismiss()
        }
        btnNope.setOnClickListener {
            Log.e("BottomSheetDialog","Button 2 clicked")
            dismiss()
        }

        val viewModelFactory =
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        timeViewModel =
                ViewModelProvider(activity!!, viewModelFactory).get(TimeViewModel::class.java)

        binding.setVariable(BR.timeVM, timeViewModel)

    }
    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateView(inflater: LayoutInflater,  container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_layout, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }

    companion object {
        val instance: BottomSheetDialog
            get() = BottomSheetDialog()
    }
}
