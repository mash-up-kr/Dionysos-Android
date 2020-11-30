package com.mashup.dionysos.ui.timelapse

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
import com.mashup.dionysos.base.viewmodel.BaseViewModel
import com.mashup.dionysos.databinding.BottomSheetTerminatedTimelapsBinding


class BottomSheetTerminatedTimeLapse : BottomSheetDialogFragment() {

    lateinit var timeLapseViewModel: TimeLapseViewModel
    lateinit var binding: BottomSheetTerminatedTimelapsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.bottom_sheet_terminated_timelaps, container, false
        )
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModelFactory =
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        timeLapseViewModel = ViewModelProvider(activity!!, viewModelFactory).get(TimeLapseViewModel::class.java)
        binding.setVariable(BR.timeLapseVM, timeLapseViewModel)

        timeLapseViewModel.bottomSheet.observe(this, Observer { it ->
            if (it == BaseViewModel.SelectBottomSheet.DISMISS) {
                dismiss()
                timeLapseViewModel.bottomSheet.value = BaseViewModel.SelectBottomSheet.NON
            }
        })
    }

    override fun getTheme(): Int =
        R.style.BottomSheetDialogTheme

    companion object {
        val instance: BottomSheetTerminatedTimeLapse
            get() = BottomSheetTerminatedTimeLapse()
    }
}
