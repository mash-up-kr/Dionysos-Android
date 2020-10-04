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
    var first = true

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
        timeLapseViewModel.changeFragment.value = TimeLapseViewModel.TimeLapseStatue.PLAY
    }

    private fun createVideo() {
        val title = timeLapseViewModel.fileName
        val folderName = basePath + title
        if (Build.VERSION.SDK_INT > 24) {
            val rc =
                FFmpeg.execute("-r 2 -start_number 00000 -i ${basePath}${title}/%05d.jpg ${basePath}${title}.mp4")

            when (rc) {
                Config.RETURN_CODE_SUCCESS -> {
                    terminateTimer()
                    Log.i(Config.TAG, "deleted")
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
        setDirEmpty(folderName)
    }

    private fun setDirEmpty(path: String) {
        val dir = File(path)
        val childFileList = dir.listFiles()
        for (j in childFileList.indices) {
            childFileList[j].delete()
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