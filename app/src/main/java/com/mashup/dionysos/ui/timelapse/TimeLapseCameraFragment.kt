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
import com.mashup.dionysos.base.viewmodel.BaseViewModel
import com.mashup.dionysos.databinding.TimelapseCameraFragmentBinding
import kotlinx.android.synthetic.main.timelapse_camera_fragment.*
import java.io.File


class TimeLapseCameraFragment :
    BaseFragment<TimelapseCameraFragmentBinding>(R.layout.timelapse_camera_fragment) {

    private lateinit var timeLapseViewModel: TimeLapseViewModel
    private val basePath = "/data/data/com.mashup.dionysos/files/"
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
                createVideo()
                timeLapseViewModel.bottomSheet.value = BaseViewModel.SelectBottomSheet.DISMISS
            } else if (it == BaseViewModel.SelectBottomSheet.NOPE) {
                timeLapseViewModel.bottomSheet.value = BaseViewModel.SelectBottomSheet.DISMISS
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        first = true
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun terminateTimer() {
        parentFragmentManager.beginTransaction().remove(this).commit()
        timeLapseViewModel.fragmentTerminate.value = true
    }

    private fun createVideo() {
        val title = timeLapseViewModel.fileName

        if (Build.VERSION.SDK_INT > 24) {

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
        } else {
        }
    }
    override fun onDestroy() {
        val title = timeLapseViewModel.fileName
        val folderName = basePath + title
        setDirEmpty(folderName)
        super.onDestroy()
    }

    private fun setDirEmpty(path: String) {
        val dir = File(path)
        val childFileList = dir.listFiles()
        childFileList?.forEach {
            it.delete()
        }
        if (dir.exists()) {
            dir.delete()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): TimeLapseCameraFragment {
            return TimeLapseCameraFragment()
        }
    }

}