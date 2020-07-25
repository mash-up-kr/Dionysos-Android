package com.mashup.dionysos.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TimeStamp(
    val id: Long,
    val url: String
) : Parcelable