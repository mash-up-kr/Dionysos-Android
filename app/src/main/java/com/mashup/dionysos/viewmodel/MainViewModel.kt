package com.mashup.dionysos.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mashup.dionysos.model.MainModel

class MainViewModel(application: Application) : BaseViewModel(application) {
    val mainModel = MutableLiveData(MainModel(131231L))


}