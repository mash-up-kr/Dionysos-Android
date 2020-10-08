package com.mashup.dionysos.ui.timelapse

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mashup.dionysos.base.viewmodel.BaseViewModel
import com.mashup.dionysos.model.PlayModel

class TimeLapseViewModel(application: Application) : BaseViewModel(application) {

    var fileDir = ""
    var fileName = "timeLapse"
        private set
    val controlTime = MutableLiveData<Long>(0)
    var mCameraFacing = MutableLiveData(1)
    var isPlay = MutableLiveData(true)
    var fragmentTerminate = MutableLiveData<Boolean>()
    var changeFragment = MutableLiveData<TimeLapseStatue>(TimeLapseStatue.CREATE)

    var bottomSheet = MutableLiveData<SelectBottomSheet>(SelectBottomSheet.NON)

    enum class TimeLapseStatue { CREATE, BOTTOM_SHEET, PLAY, TERMINATED }


    val timeDataModel =
        MutableLiveData(PlayModel(playStatus = false, increase = true, totalTime = 0L))

    fun setFileName(string: String) {
        val a = getApplication<Application>()
        a.applicationContext
        fileName = string
    }

    fun onClickSelectBottomSheet(select: SelectBottomSheet) {
        bottomSheet.value = select
    }

    fun onClickRecodeFlip() {
        mCameraFacing.value = if (mCameraFacing.value == 0) 1 else 0
        Log.e("mCameraFacing.value", "${mCameraFacing.value}")
    }

    fun onClickRecodeStop() {
        Log.e("onClickRecodeStop", "  ")
        isPlay.value = !isPlay.value!!
    }

    fun onClickRecodeClose() {
        Log.e("onClickRecodeStop", "  ")
        changeFragment.value = TimeLapseStatue.BOTTOM_SHEET
    }

}

