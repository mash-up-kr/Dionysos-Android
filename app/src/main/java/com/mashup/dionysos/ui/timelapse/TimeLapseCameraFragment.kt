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


class TimeLapseCameraFragment :
    BaseFragment<TimelapseCameraFragmentBinding>(R.layout.timelapse_camera_fragment) {

    private lateinit var timeLapsViewModel: TimeLapseViewModel
    var first = true

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewModelFactory =
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        timeLapsViewModel =
            ViewModelProvider(activity!!, viewModelFactory).get(TimeLapseViewModel::class.java)
        binding.setVariable(BR.timeLapseVM, timeLapsViewModel)
        mCamera.timeLapsViewModel = timeLapsViewModel

//        val bottomSheet = BottomSheetTimeLapse()
//        bottomSheet.timeViewModel = timeViewModel
        Log.e("fragment  ", "onActivityCreated $first")


        timeLapsViewModel.mCameraFacing.observe(this, Observer {
            if (!first) {
                Log.e("fragment  ", " timeLapsViewModel.mCameraFacing.observe $first")
                terminateTimer()
            }
            first = false
        })


        timeLapsViewModel.mCameraStop.observe(this, Observer {
            Log.e("mCameraStop  ", "  $it")
            if (it) {
                mCamera.stop()
                timeLapsViewModel.mCameraStop.value = false
                checkFormat()
            }
        })

    }

    private fun terminateTimer() {
        Log.e("fragment  ", "terminateTimer()")
        parentFragmentManager.beginTransaction().remove(this).commit()
        timeLapsViewModel.fragmentTerminate.value = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        Log.e("fragment  ", "onCreateView")
        first = true
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroy() {
        Log.e("fragment  ", "onDestroy")
        super.onDestroy()
    }

    companion object {
        @JvmStatic
        fun newInstance(): TimeLapseCameraFragment {
            return TimeLapseCameraFragment()
        }
    }

    private fun checkFormat() {
        if (Build.VERSION.SDK_INT > 24) {
            val title = timeLapsViewModel.fileName
            val rc =
                FFmpeg.execute("-r 2 -start_number 00000 -i ${basePath}${title}/%05d.jpg ${basePath}${title}.mp4")

            when (rc) {
                Config.RETURN_CODE_SUCCESS -> {
                    val folderName = basePath + title
                    setDirEmpty(folderName)
                    Log.i(Config.TAG, "deleted")
                    Log.i(Config.TAG, "Command execution completed successfully.")
                }
                Config.RETURN_CODE_CANCEL -> {
                    Log.i(
                        Config.TAG,
                        "Command execution cancelled by user."
                    )
                }
                else -> {
                    Log.i(
                        Config.TAG, String.format(
                            "Command execution failed with rc=%d and the output below.", rc
                        )
                    )
                    Config.printLastCommandOutput(Log.INFO)
                }
            }
        } else {
        }
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
}