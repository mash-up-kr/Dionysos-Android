package com.mashup.dionysos.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mashup.dionysos.R
import com.mashup.dionysos.api.MogakgongApi
import com.mashup.dionysos.base.viewmodel.BaseViewModel
import com.mashup.dionysos.model.MainModel
import com.mashup.dionysos.model.PlayModel
import com.mashup.dionysos.model.TimerSetting

class TimeViewModel(application: Application) : BaseViewModel(application) {
    val controlTime = MutableLiveData<Long>(0)

    lateinit var repository: MogakgongApi
    val timeData =
        MutableLiveData(PlayModel(playStatus = false, increase = true, totalTime = 0L))
    val mainBottom = MutableLiveData(MainModel(ranking = false, home = true, myPage = false))

    //페이지이동
    var fragmentChange = MutableLiveData<SelectFragment>()
    var showMainTabBar = MutableLiveData<Boolean>()

    val timerClickable = MutableLiveData(TimerSetting())
    var timeLapse = MutableLiveData<SelectBottomSheet>()
    var timerStop = MutableLiveData<SelectBottomSheet>()

    enum class SelectFragment { Home, Setting, STOP, TIMER, BottomSheet }

    var playerStatus = MutableLiveData<Int>()
    var popFragment = MutableLiveData<Boolean>()

    var timerSettingHours = MutableLiveData<String>()
    var timerSettingMin = MutableLiveData<String>()
    var timerSettingSec = MutableLiveData<String>()

    var changeFragment = MutableLiveData<Int>(2)
    var newFragment = true

    init {
        showMainTabBar.value = true
    }

    fun editMyPageClick(int: Int){
        changeFragment.value = int
    }

    fun timeBottomClick(int: Int) {
        changeFragment.value = int
        val a = mainBottom.value!!
        when (int) {
            1 -> {
               a.ranking = true
               a.home = false
               a.myPage = false
            }
            2 -> {
                a.ranking = false
                a.home = true
                a.myPage = false
            }
            3 -> {
                a.ranking = false
                a.home = false
                a.myPage = true
            }
        }
        mainBottom.value = a
    }

    fun getTextColor(boolean: Boolean): Int {
        return if (boolean)
            getApplication<Application>().getColor(R.color.coral_pink)
        else
            getApplication<Application>().getColor(R.color.dark_grey)
    }

    fun timerSettingNull() {
        timerSettingHours.value = null
        timerSettingMin.value = null
        timerSettingSec.value = null
    }

    fun onClickTimerSettingSave() {
        if (timerClickable.value!!.clickable) {
            val h = timerSettingHours.value?.toInt() ?: 0
            val m = timerSettingMin.value?.toInt() ?: 0
            val s = timerSettingSec.value?.toInt() ?: 0
            val totalSettingTime = timerClickable.value!!.getTotalTime(h, m, s)
            timeData.value!!.timer = totalSettingTime
            timeData.value!!.increase = false
            timeData.value = timeData.value
            onClickSelectTimerFragment(SelectFragment.TIMER)
        }
    }

    private fun onClickFragmentStopWatch() {
        timeData.value!!.timer = 0
        timeData.value!!.increase = true
        timeData.value = timeData.value
        Log.e("qwew", "" + timeData.value!!)

    }


    fun onClickSelectTimerFragment(select: SelectFragment) {
        when (select) {
            SelectFragment.BottomSheet -> onClickFragmentStopWatch()
        }
        fragmentChange.value = select
    }

    fun onClickSelectTimeLapse(select: SelectBottomSheet) {
        timeLapse.value = select
    }

    fun onClickSelectTimerStop(select: SelectBottomSheet) {
        timerStop.value = select
    }

    fun onClickPlayer(position: Int) {
        Log.e("position", "position $position")
        var base = timeData.value!!
        base.playStatus = position == 1
        timeData.value = base
        playerStatus.value = position
        //0: Pause 1:Play 2:Stop
    }
}