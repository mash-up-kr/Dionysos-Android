package com.mashup.dionysos.base.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mashup.dionysos.api.MogakgongApi

abstract class BaseViewModel protected constructor(application: Application) :
    AndroidViewModel(application) {
    enum class SelectBottomSheet { NON, YEAH, NOPE, DISMISS }
}