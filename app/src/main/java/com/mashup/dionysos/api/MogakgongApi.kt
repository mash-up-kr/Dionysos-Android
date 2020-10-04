package com.mashup.dionysos.api

import com.mashup.dionysos.api.dto.*
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*


interface MogakgongApi {
    @POST("/user/check/nickname")
    fun reqNicknameCheck(@Body reqNicknameCheck: ReqNicknameCheck): Single<ResNicknameCheck>

    @POST("/user/signup")
    fun reqSignUp(@Body reqSignUp: ReqSignUp): Single<ResUser>

    @POST("/user/signin")
    fun reqSignIn(@Body reqSignIn: ReqSignIn): Single<ResUser>

    @PUT("/user/my")
    fun reqEditNickName(@Body reqSignIn: ReqEditNickName): Single<ResUser>

    @GET("/user/my")
    fun reqGetNickName(): Single<ResUser>

}
