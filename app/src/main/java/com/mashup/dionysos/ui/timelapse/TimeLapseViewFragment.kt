package com.mashup.dionysos.ui.timelapse

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.arthenica.mobileffmpeg.Config
import com.arthenica.mobileffmpeg.FFmpeg
import com.mashup.dionysos.BR
import com.mashup.dionysos.R
import com.mashup.dionysos.base.fragment.BaseFragment
import com.mashup.dionysos.databinding.TimelapseCameraFragmentBinding
import kotlinx.android.synthetic.main.timelapse_camera_fragment.*
import java.io.File


class TimeLapseViewFragment :
    BaseFragment<TimelapseCameraFragmentBinding>(R.layout.timelapse_camera_fragment) {

    private lateinit var timeLapsViewModel: TimeLapseViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewModelFactory =
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        timeLapsViewModel =
            ViewModelProvider(activity!!, viewModelFactory).get(TimeLapseViewModel::class.java)
        binding.setVariable(BR.timeLapseVM, timeLapsViewModel)
        mCamera.timeLapsViewModel = timeLapsViewModel
    }

    companion object {
        @JvmStatic
        fun newInstance(): TimeLapseViewFragment {
            return TimeLapseViewFragment()
        }
    }

}