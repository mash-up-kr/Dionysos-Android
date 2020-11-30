package com.mashup.dionysos.ui.setting

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mashup.dionysos.api.dto.UserInfo
import com.mashup.dionysos.base.viewmodel.BaseViewModel

class SettingViewModel (application: Application) : BaseViewModel(application) {
    var changeFragment = MutableLiveData<Int>(0)

    fun changeFragment(int: Int){
        changeFragment.value = int
    }
}
