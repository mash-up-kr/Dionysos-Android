package com.mashup.dionysos.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel

abstract class BaseViewModel protected constructor(application: Application) :
    AndroidViewModel(application) {
}