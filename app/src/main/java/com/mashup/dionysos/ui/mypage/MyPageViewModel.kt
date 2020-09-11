package com.mashup.dionysos.ui.mypage

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mashup.dionysos.api.dto.UserInfo
import com.mashup.dionysos.base.viewmodel.BaseViewModel

class MyPageViewModel (application: Application) : BaseViewModel(application) {
    val userInfo = MutableLiveData<UserInfo>()

}
