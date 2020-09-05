package com.mashup.dionysos.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mashup.dionysos.model.MainModel
import com.mashup.dionysos.model.PlayModel
import com.mashup.dionysos.model.TimerSetting
import com.mashup.dionysos.base.viewmodel.BaseViewModel

class TimeViewModel(application: Application) : BaseViewModel(application) {
    val controlTime = MutableLiveData<Long>(0)

    val timeDataModel = MutableLiveData(PlayModel(playStatus = false, increase = true, totalTime = 0L))
    val mainBottom = MutableLiveData(MainModel(ranking = false, home = true, myPage = false))

    //페이지이동
    var fragmentChange = MutableLiveData<Boolean>()
    var showMainTabBar = MutableLiveData<Boolean>()

    val timerClickable = MutableLiveData(TimerSetting())
    var timeLaps = MutableLiveData<Boolean>()
    var playerStatus = MutableLiveData<Int>()
    var isChild = MutableLiveData<Boolean>()

    var timerSettingHours = MutableLiveData<String>()
    var timerSettingMin = MutableLiveData<String>()
    var timerSettingSec = MutableLiveData<String>()

    var changeFragment = MutableLiveData<Int>(2)

    init {
        showMainTabBar.value = true
    }

    fun timeBottomClick(int: Int) {
        com.google.android.exoplayer2.util.Log.e("1322", "timeBottomClick")
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

    fun onClickTimerSettingSave() {
        if (timerClickable.value!!.clickable) {
            val h = timerSettingHours.value?.toInt() ?: 0
            val m = timerSettingMin.value?.toInt() ?: 0
            val s = timerSettingSec.value?.toInt() ?: 0
            val totalSettingTime = timerClickable.value!!.getTotalTime(h, m, s)
            timeDataModel.value!!.timer = totalSettingTime
            timeDataModel.value!!.increase = false
            timeDataModel.value = timeDataModel.value
        }
    }

    fun onClickSelectTimerFragment(select: Boolean) {
        fragmentChange.value = select
    }

    fun onClickSelectTimalaps(select: Boolean) {
        timeLaps.value = select
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