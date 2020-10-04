package com.mashup.dionysos.ui.timelapse

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mashup.dionysos.R
import com.mashup.dionysos.base.activity.BaseActivity
import com.mashup.dionysos.databinding.ActivityTimelapseBinding

class TimeLapseActivity : BaseActivity<ActivityTimelapseBinding>(R.layout.activity_timelapse) {
    companion object {
        private const val TAG = "TIME_LAPSE_ACTIVITY"
    }

    private lateinit var timeLapsViewModel: TimeLapseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        initMainDataBinding()
        observers()
        startFragment()
    }

    private fun observers() {
        timeLapsViewModel.fragmentTerminate.observe(this, Observer {
            startFragment()
        })
    }

    private fun startFragment() {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentTimeLapse, TimeLapseCameraFragment()).commit()
    }

    private fun initMainDataBinding() {
        val viewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        timeLapsViewModel =
            ViewModelProvider(this, viewModelFactory).get(TimeLapseViewModel::class.java)
        binding.timeLapseVM = timeLapsViewModel
    }
}
