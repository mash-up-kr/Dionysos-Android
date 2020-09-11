package com.mashup.dionysos.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mashup.dionysos.base.viewmodel.BaseViewModel
import com.mashup.dionysos.model.MainModel
import com.mashup.dionysos.model.PlayModel
import com.mashup.dionysos.model.TimerSetting

class TimeViewModel(application: Application) : BaseViewModel(application) {
    val controlTime = MutableLiveData<Long>(0)

    val timeDataModel =
        MutableLiveData(PlayModel(playStatus = false, increase = true, totalTime = 0L))
    val mainBottom = MutableLiveData(MainModel(ranking = false, home = true, myPage = false))

    //페이지이동
    var fragmentChange = MutableLiveData<SelectFragment>()
    var showMainTabBar = MutableLiveData<Boolean>()

    val timerClickable = MutableLiveData(TimerSetting())
    var timeLapse = MutableLiveData<SelectTimeLapse>()

    enum class SelectTimeLapse { NON, YEAH, NOPE, DISMISS }
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
            timeDataModel.value!!.timer = totalSettingTime
            timeDataModel.value!!.increase = false
            timeDataModel.value = timeDataModel.value
            onClickSelectTimerFragment(SelectFragment.TIMER)
        }
    }

    private fun onClickFragmentStopWatch() {
        timeDataModel.value!!.timer = 0
        timeDataModel.value!!.increase = false
        timeDataModel.value = timeDataModel.value
    }


    fun onClickSelectTimerFragment(select: SelectFragment) {
        when (select) {
            SelectFragment.STOP -> onClickFragmentStopWatch()
            else -> fragmentChange.value = select
        }
    }

    fun onClickSelectTimeLapse(select: SelectTimeLapse) {
        timeLapse.value = select
    }

    fun onClickPlayer(position: Int) {
        Log.e("position", "position $position")
        var base = timeDataModel.value!!
        base.playStatus = position == 1
        timeDataModel.value = base
        playerStatus.value = position
        //0: Pause 1:Play 2:Stop
    }
}