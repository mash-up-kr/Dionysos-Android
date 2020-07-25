package com.mashup.dionysos.model

data class PlayModel(
        var playStatus:Boolean = false,
        var increase:Boolean = true,
        var totalTime: Long = 0L,
        var timer:Long = 0L
)