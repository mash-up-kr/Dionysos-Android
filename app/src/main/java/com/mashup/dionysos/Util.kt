package com.mashup.dionysos

import java.util.concurrent.TimeUnit

object Util {
    @JvmStatic
    fun getTime(milliseconds: Long): String {
        val hours = TimeUnit.MILLISECONDS.toHours(milliseconds)
        val toMinutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
        val toSeconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds)
        return String.format(
            "%d:%02d.%02d",
            hours,
            toMinutes - TimeUnit.HOURS.toMinutes(hours),
            toSeconds - TimeUnit.MINUTES.toSeconds(toMinutes)
        )
    }

}