package com.mashup.dionysos.ui.mypage

import android.graphics.Color
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter

@BindingAdapter("app:studyTime")
fun ImageView.setCellBackground(studyTime: Int) {
    when (studyTime) {
        0 -> {
            setColorFilter(Color.parseColor("#f2f2f2"), android.graphics.PorterDuff.Mode.SRC_IN)
        }
        in 1..2 -> {
            setColorFilter(Color.parseColor("#fedee2"), android.graphics.PorterDuff.Mode.SRC_IN)
        }
        in 3..5 -> {
            setColorFilter(Color.parseColor("#fd9ca8"), android.graphics.PorterDuff.Mode.SRC_IN)
        }
        in 6..8 -> {
            setColorFilter(Color.parseColor("#fc5a6e"), android.graphics.PorterDuff.Mode.SRC_IN)
        }
        else -> {
            setColorFilter(Color.parseColor("#212226"), android.graphics.PorterDuff.Mode.SRC_IN)
        }
    }
}
