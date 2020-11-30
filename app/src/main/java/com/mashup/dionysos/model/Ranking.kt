package com.mashup.dionysos.model

data class Ranking(
    val id: Long,
    val rate: Int,
    val nickname: String,
    val profileUrl: String?,
    val time: Long,
    val isMine: Boolean = false
)