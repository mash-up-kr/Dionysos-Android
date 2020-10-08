package com.mashup.dionysos.ui.timelapse

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.MediaController
import androidx.lifecycle.ViewModelProvider
import com.mashup.dionysos.BR
import com.mashup.dionysos.R
import com.mashup.dionysos.base.fragment.BaseFragment
import com.mashup.dionysos.databinding.TimelapseViewFragmentBinding
import kotlinx.android.synthetic.main.timelapse_view_fragment.*


class TimeLapseViewFragment :
    BaseFragment<TimelapseViewFragmentBinding>(R.layout.timelapse_view_fragment) {

    private lateinit var timeLapsViewModel: TimeLapseViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewModelFactory =
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        timeLapsViewModel =
            ViewModelProvider(activity!!, viewModelFactory).get(TimeLapseViewModel::class.java)
        binding.setVariable(BR.timeLapseVM, timeLapsViewModel)

        val mc = MediaController(activity)
        val path = "${timeLapsViewModel.fileDir}/${timeLapsViewModel.fileName}.mp4"
        Log.e("path",path)
        videoView.setVideoURI(Uri.parse(path))
        videoView.setMediaController(mc)
        videoView.start()
    }

    companion object {
        @JvmStatic
        fun newInstance(): TimeLapseViewFragment {
            return TimeLapseViewFragment()
        }
    }

}