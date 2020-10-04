package com.mashup.dionysos.api.dto

data class ResNicknameCheck(
    val result: Boolean=false,
    val error:ErrorCode
)