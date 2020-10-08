package com.mashup.dionysos.ui.main

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mashup.dionysos.BR
import com.mashup.dionysos.R
import com.mashup.dionysos.api.dto.ReqSaveTimeHistory
import com.mashup.dionysos.base.fragment.BaseFragment
import com.mashup.dionysos.base.viewmodel.BaseViewModel
import com.mashup.dionysos.databinding.FragmentTimeControlBinding
import com.mashup.dionysos.model.PlayModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import kotlin.coroutines.CoroutineContext

class TimeControlFragment :
        BaseFragment<FragmentTimeControlBinding>(R.layout.fragment_time_control), CoroutineScope {

    private val TAG = "TimeControlFragment"
    private val increaseTime = 1000L
    private lateinit var job: Job
    private lateinit var timeViewModel: TimeViewModel

    var playStatus = false
    var increase = true
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModelFactory =
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        timeViewModel =
            ViewModelProvider(activity!!, viewModelFactory).get(TimeViewModel::class.java)
        binding.setVariable(BR.timeVM, timeViewModel)
        increase = timeViewModel.timeData.value!!.increase
        val bottomSheet = BottomSheetStop()

        timeViewModel.controlTime.value = timeViewModel.timeData.value!!.timer
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
                    bottomSheet.show(fragmentManager!!, "exampleBottomSheet")
                }
            }

        })
        timeViewModel.timerStop.observe(this, Observer { it ->
            if (it == BaseViewModel.SelectBottomSheet.YEAH) {
                terminateTimer()
                val duration: Int = timeViewModel.timeData.value!!.totalTime.toInt()
                val timeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                val historyDay = timeFormat.format(System.currentTimeMillis())
                timeViewModel.repository.reqSaveTimeHistory(
                    ReqSaveTimeHistory(duration, historyDay)
                )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Log.e("sueecss   ", "  ")
                    }, { e ->
                        e.printStackTrace()
                    })

                timeViewModel.timerStop.value = BaseViewModel.SelectBottomSheet.DISMISS
            } else if (it == BaseViewModel.SelectBottomSheet.NOPE) {
                timeViewModel.timerStop.value = BaseViewModel.SelectBottomSheet.DISMISS
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

    private fun terminateTimer() {
        timeViewModel.playerStatus.value = -1
        job.cancel()
        parentFragmentManager.beginTransaction().remove(this).commit()
        parentFragmentManager.popBackStack()
        timeViewModel.showMainTabBar.value = true
    }

    private fun increasePlayTime() {
        timeViewModel?.let { it_ ->
            var base = it_.controlTime.value!!
            val timeData = it_.timeData.value!!

            if (increase) {
                base += increaseTime
                timeData.totalTime += increaseTime
            } else if (!increase && timeData.timer <= 0) {
                if (!timeData.timeOver) {
                    timeData.timeOver = true
                }
                base += increaseTime
                timeData.totalTime += increaseTime
            } else {
                base -= increaseTime
                timeData.timer -= increaseTime
                timeData.totalTime += increaseTime
            }
            it_.controlTime.postValue(base)
            it_.timeData.postValue(timeData)
        }
    }

    override fun onDetach() {
        Log.e(TAG, "  onDetach")
        job.cancel()
        timeViewModel.timeData.value = PlayModel(
            playStatus = false,
            totalTime = this.timeViewModel.timeData.value!!.totalTime,
            increase = true,
            timeOver = false
        )
        super.onDetach()
    }

    companion object {
        @JvmStatic
        fun newInstance(): TimeControlFragment {
            return TimeControlFragment()
        }
    }
}
