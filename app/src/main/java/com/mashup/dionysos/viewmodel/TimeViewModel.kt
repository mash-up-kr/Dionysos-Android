package com.mashup.dionysos.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mashup.dionysos.model.PlayModel
import com.mashup.dionysos.model.TimerSetting

class TimeViewModel(application: Application) : BaseViewModel(application) {
    val controlTime = MutableLiveData<Long>(0)

    val timeDataModel = MutableLiveData(PlayModel(playStatus = false, increase = true, totalTime = 0L))

    //페이지이동
    var fragmentChange = MutableLiveData<Boolean>()
    var showMainTapbar = MutableLiveData<Boolean>()

    val timerClickable = MutableLiveData(TimerSetting())
    var timeLaps = MutableLiveData<Boolean>()
    var playerStatus = MutableLiveData<Int>()
    var isChild = MutableLiveData<Boolean>()

    var _timerSettingHours = MutableLiveData<String>()
    var _timerSettingMin = MutableLiveData<String>()
    var _timerSettingSec = MutableLiveData<String>()

    init {
        showMainTapbar.value = true
    }

    fun onClickTimerSettingSave() {
        if (timerClickable.value!!.clickable) {
            val h = _timerSettingHours.value?.toInt() ?: 0
            val m = _timerSettingMin.value?.toInt() ?: 0
            val s = _timerSettingSec.value?.toInt() ?: 0
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