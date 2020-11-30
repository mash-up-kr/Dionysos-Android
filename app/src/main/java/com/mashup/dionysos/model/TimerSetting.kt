package com.mashup.dionysos.model

import com.mashup.dionysos.R

data class TimerSetting(
        var clickable: Boolean = false
) {
    fun getTimerSettingBtn(): Int {
        return if (clickable) R.drawable.ic_btn_done_on else R.drawable.ic_btn_done_off
    }

    fun getTotalTime(hours:Int,min:Int,sec:Int): Long {
        return (((hours * 60 + min) * 60) + sec) * 1000.toLong()
    }
}