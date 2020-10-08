package com.mashup.dionysos.ui.timelapse

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mashup.dionysos.BR
import com.mashup.dionysos.R
import com.mashup.dionysos.base.fragment.BaseFragment
import com.mashup.dionysos.base.viewmodel.BaseViewModel
import com.mashup.dionysos.databinding.TimelapseCameraFragmentBinding
import kotlinx.android.synthetic.main.timelapse_camera_fragment.*
import java.io.File


class TimeLapseCameraFragment :
    BaseFragment<TimelapseCameraFragmentBinding>(R.layout.timelapse_camera_fragment) {

    private lateinit var timeLapseViewModel: TimeLapseViewModel
    private var first = true

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewModelFactory =
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        timeLapseViewModel =
            ViewModelProvider(activity!!, viewModelFactory).get(TimeLapseViewModel::class.java)
        binding.setVariable(BR.timeLapseVM, timeLapseViewModel)
        mCamera.timeLapsViewModel = timeLapseViewModel


        timeLapseViewModel.mCameraFacing.observe(this, Observer {
            if (!first) {
                terminateTimer()
                timeLapseViewModel.fragmentTerminate.value = true
            }
            first = false
        })

        timeLapseViewModel.isPlay.observe(this, Observer {
            mCamera.playStatus = it
        })
        timeLapseViewModel.bottomSheet.observe(this, Observer {
            Log.e("..", "bottomSheet $it")
            if (it == BaseViewModel.SelectBottomSheet.YEAH) {
                mCamera.stop()
                terminateTimer()
                timeLapseViewModel.changeFragment.value = TimeLapseViewModel.TimeLapseStatue.EDIT
                timeLapseViewModel.bottomSheet.value = BaseViewModel.SelectBottomSheet.DISMISS
            } else if (it == BaseViewModel.SelectBottomSheet.NOPE) {
                timeLapseViewModel.bottomSheet.value = BaseViewModel.SelectBottomSheet.DISMISS
            }
        })
    }
    private fun terminateTimer() {
        parentFragmentManager.beginTransaction().remove(this).commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        first = true
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    companion object {
        @JvmStatic
        fun newInstance(): TimeLapseCameraFragment {
            return TimeLapseCameraFragment()
        }
    }

}