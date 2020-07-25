package com.mashup.dionysos.fragment

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mashup.dionysos.BR
import com.mashup.dionysos.R
import com.mashup.dionysos.databinding.FragmentTimeControlBinding
import com.mashup.dionysos.viewmodel.TimeViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class TimeControlFragment :
        BaseFragment<FragmentTimeControlBinding>(R.layout.fragment_time_control), CoroutineScope {

    private val increaseTime = 1000L
    private lateinit var job: Job
    lateinit var timeViewModel: TimeViewModel

    var playStatus = false
    var increase = true
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModelFactory =
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        timeViewModel = ViewModelProvider(activity!!, viewModelFactory).get(TimeViewModel::class.java)
        binding.setVariable(BR.timeVM, timeViewModel)
        increase = timeViewModel.timeDataModel.value!!.increase

        if (!increase) {
            timeViewModel.controlTime.value = timeViewModel.timeDataModel.value!!.timer
        }
        timeViewModel.playerStatus.observe(this, Observer { it ->
            when (it) {
                0 -> {
                    playStatus = false
                }
                1 -> {
                    playStatus = true
                    job.start()
                }
                2 -> {
                    job.cancel()
                }
            }

        })

        job = CoroutineScope(Dispatchers.Default).launch {
            while (isActive) {
                if (playStatus) {
                    increasePlayTime()
                }
                Thread.sleep(increaseTime)
            }
        }
    }

    private fun increasePlayTime() {
        timeViewModel?.let { it_ ->
            var base = it_.controlTime.value!!
            val timeDataModel = it_.timeDataModel.value!!
            if (increase) {
                base += increaseTime
                timeDataModel.totalTime += increaseTime
            } else {
                base -= increaseTime
                timeDataModel.timer -= increaseTime
                timeDataModel.totalTime += increaseTime
            }
            it_.controlTime.postValue(base)
            it_.timeDataModel.postValue(timeDataModel)
            Log.e("TimeControlFragment", "   it_.controlTime${it_.controlTime.value}")

        }
    }

    override fun onDetach() {
        Log.e("TimeControlFragment", "  onDetach")
        job.cancel()
        super.onDetach()
    }

    companion object {
        @JvmStatic
        fun newInstance(): TimeControlFragment {
            return TimeControlFragment()
        }
    }
}
