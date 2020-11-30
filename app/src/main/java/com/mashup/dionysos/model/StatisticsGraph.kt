package com.mashup.dionysos.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StatisticsGraph(
    val day: String,
    val studyTime: Int = 0
) : Parcelable