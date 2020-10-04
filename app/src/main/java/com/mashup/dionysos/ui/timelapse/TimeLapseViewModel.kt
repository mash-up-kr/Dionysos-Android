package com.mashup.dionysos.ui.timelapse

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mashup.dionysos.base.viewmodel.BaseViewModel
import com.mashup.dionysos.model.PlayModel

class TimeLapseViewModel(application: Application) : BaseViewModel(application) {
    var mCameraFacing = MutableLiveData<Int>(1)
    var mCameraStop = MutableLiveData<Boolean>(false)
    var fragmentTerminate = MutableLiveData<Boolean>()
    val controlTime = MutableLiveData<Long>(0)

    val fileName = "how"

    val timeDataModel =
        MutableLiveData(PlayModel(playStatus = false, increase = true, totalTime = 0L))


    fun onClickRecodeFlip() {
        mCameraFacing.value = if (mCameraFacing.value == 0) 1 else 0
        Log.e("mCameraFacing.value", "${mCameraFacing.value}")
    }

    fun onClickRecodeStop() {
        Log.e("onClickRecodeStop","  ")
        mCameraStop.value = true
    }
}
