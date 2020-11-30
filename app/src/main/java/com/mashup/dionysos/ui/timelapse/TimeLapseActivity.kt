package com.mashup.dionysos.ui.timelapse

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mashup.dionysos.ForecdTerminationService
import com.mashup.dionysos.R
import com.mashup.dionysos.base.activity.BaseActivity
import com.mashup.dionysos.databinding.ActivityTimelapseBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class TimeLapseActivity : BaseActivity<ActivityTimelapseBinding>(R.layout.activity_timelapse) {
    companion object {
        private const val TAG = "TIME_LAPSE_ACTIVITY"
    }

    private lateinit var timeLapseViewModel: TimeLapseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initMainDataBinding()
        observers()
        startService(Intent(this, ForecdTerminationService::class.java))
        startFragment(TimeLapseCameraFragment())

        val simpleDate = SimpleDateFormat("MMddhhmmss")
        val getTime = simpleDate.format(Date(System.currentTimeMillis()))

        val a = getExternalFilesDir(Environment.DIRECTORY_MOVIES)?.absolutePath
        Log.e("12312", "    " + a)
        timeLapseViewModel.fileDir = a ?: basePath
        timeLapseViewModel.setFileName(getTime)
    }

    override fun onDestroy() {
        Log.e("12312", "    " + "onDestroy")

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
    private fun observers() {
        timeLapseViewModel.fragmentTerminate.observe(this, Observer {
            startFragment(TimeLapseCameraFragment())
        })

        timeLapseViewModel.changeFragment.observe(this, Observer {
            when (it) {
                TimeLapseViewModel.TimeLapseStatue.BOTTOM_SHEET -> {
                    Log.e("BOTTOM_SHEET", "BOTTOM_SHEET")
                    val fragmentManager = supportFragmentManager
                    val bottomSheet = BottomSheetTerminatedTimeLapse()
                    bottomSheet.timeLapseViewModel = timeLapseViewModel
                    bottomSheet.show(fragmentManager, " timeLapseBottomSheet")
                }
                TimeLapseViewModel.TimeLapseStatue.PLAY -> {
                    startFragment(TimeLapseViewFragment())
                }
                TimeLapseViewModel.TimeLapseStatue.EDIT -> {
                    startFragment(TimeLapseEditFragment())
                }
                else -> {

                }
            }
        })
    }

    private fun startFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentTimeLapse, fragment).commit()
    }

    private fun initMainDataBinding() {
        val viewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        timeLapseViewModel =
            ViewModelProvider(this, viewModelFactory).get(TimeLapseViewModel::class.java)
        binding.timeLapseVM = timeLapseViewModel
    }
}
