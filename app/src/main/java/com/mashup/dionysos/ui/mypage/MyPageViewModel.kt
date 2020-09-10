package com.mashup.dionysos.ui.mypage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mashup.dionysos.api.dto.UserInfo

class MyPageViewModel : ViewModel() {
    val userInfo = MutableLiveData<UserInfo>()
}
