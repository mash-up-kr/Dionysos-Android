package com.mashup.dionysos.api

import com.mashup.dionysos.api.dto.ReqNicknameCheck
import com.mashup.dionysos.api.dto.ResNicknameCheck
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.POST


interface MogakgongApi {
    @POST("/user/check/nickname")
    fun reqNicknameCheck(@Body reqNicknameCheck: ReqNicknameCheck): Single<ResNicknameCheck>
}
