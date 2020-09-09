package com.mashup.dionysos.api.dto

data class UserInfo(
    val jwt:String,
    val nickname:String,
    val provider:String,
    val uid:String
)