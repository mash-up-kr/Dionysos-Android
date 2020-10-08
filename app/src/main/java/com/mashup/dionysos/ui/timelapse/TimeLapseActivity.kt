package com.mashup.dionysos.ui.timelapse

import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mashup.dionysos.R
import com.mashup.dionysos.base.activity.BaseActivity
import com.mashup.dionysos.databinding.ActivityTimelapseBinding
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
        startFragment(TimeLapseViewModel.TimeLapseStatue.CREATE)
        val simpleDate = SimpleDateFormat("MMddhhmmss")
        val getTime = simpleDate.format(Date(System.currentTimeMillis()))

        val a = getExternalFilesDir(Environment.DIRECTORY_MOVIES)?.absolutePath
        Log.e("12312", "    " + a)
        timeLapseViewModel.fileDir = a ?: basePath
        timeLapseViewModel.setFileName(getTime)
    }

    private fun observers() {
        timeLapseViewModel.fragmentTerminate.observe(this, Observer {
            startFragment(TimeLapseViewModel.TimeLapseStatue.CREATE)
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
                    startFragment(TimeLapseViewModel.TimeLapseStatue.PLAY)
                }
                else -> {

                }
            }
        })
    }

    private fun startFragment(create: TimeLapseViewModel.TimeLapseStatue) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        if (create == TimeLapseViewModel.TimeLapseStatue.CREATE) {
            transaction.replace(R.id.fragmentTimeLapse, TimeLapseCameraFragment()).commit()
        } else if (create == TimeLapseViewModel.TimeLapseStatue.PLAY) {
            transaction.replace(R.id.fragmentTimeLapse, TimeLapseViewFragment()).commit()
        }
    }

    private fun initMainDataBinding() {
        val viewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        timeLapseViewModel =
            ViewModelProvider(this, viewModelFactory).get(TimeLapseViewModel::class.java)
        binding.timeLapseVM = timeLapseViewModel
    }
}
