package com.mashup.dionysos.ui.timelapse

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.arthenica.mobileffmpeg.Config
import com.arthenica.mobileffmpeg.FFmpeg
import com.mashup.dionysos.BR
import com.mashup.dionysos.R
import com.mashup.dionysos.base.fragment.BaseFragment
import com.mashup.dionysos.databinding.TimelapseEditFragmentBinding
import kotlinx.android.synthetic.main.timelapse_edit_fragment.*


class TimeLapseEditFragment :
    BaseFragment<TimelapseEditFragmentBinding>(R.layout.timelapse_edit_fragment) {

    private lateinit var timeLapseViewModel: TimeLapseViewModel
    private val basePath = "/data/data/com.mashup.dionysos/files/"

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewModelFactory =
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        timeLapseViewModel =
            ViewModelProvider(activity!!, viewModelFactory).get(TimeLapseViewModel::class.java)
        binding.setVariable(BR.timeLapseVM, timeLapseViewModel)

        btnPlay.setOnClickListener {
            createVideo()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): TimeLapseEditFragment {
            return TimeLapseEditFragment()
        }
    }

    private fun terminateTimer() {
        parentFragmentManager.beginTransaction().remove(this).commit()
    }

    private fun createVideo() {
        val title = timeLapseViewModel.fileName
        val master = "${timeLapseViewModel.fileDir}/${title}.mp4"
        val rc =
            FFmpeg.execute("-r 12 -start_number 00000 -i ${basePath}${title}/%05d.jpg $master")

        when (rc) {
            Config.RETURN_CODE_SUCCESS -> {
                terminateTimer()
                timeLapseViewModel.changeFragment.value =
                    TimeLapseViewModel.TimeLapseStatue.PLAY
                Log.e("RETURN_CODE_SUCCESS", "  $master")
                Log.i(Config.TAG, "Command execution completed successfully.")
            }
            Config.RETURN_CODE_CANCEL -> {
                Log.i(Config.TAG, "Command execution cancelled by user.")
            }
            else -> {
                Log.i(
                    Config.TAG,
                    String.format(
                        "Command execution failed with rc=%d and the output below.", rc
                    )
                )
                Config.printLastCommandOutput(Log.INFO)
            }
        }
    }


}