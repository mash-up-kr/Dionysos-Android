package com.mashup.dionysos

import android.util.Log
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

    @JvmStatic
    fun getTimeH(milliseconds: Long): String {
        Log.e("!getTimeH","milliseconds$milliseconds")
        val hours = TimeUnit.MILLISECONDS.toHours(milliseconds)
        return String.format(
                "%02d",
                hours
        )
    }
    @JvmStatic
    fun getTimeMS(milliseconds: Long): String {
        val hours = TimeUnit.MILLISECONDS.toHours(milliseconds)
        val toMinutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
        val toSeconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds)
        return String.format(
                ":%02d.%02d",
                toMinutes - TimeUnit.HOURS.toMinutes(hours),
                toSeconds - TimeUnit.MINUTES.toSeconds(toMinutes)
        )
    }
}