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

    @Headers("Authorization:eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNTk5ODU1NzQwLCJleHAiOjE2MDI0NDc3NDAsInVpZCI6IktBS0FPXzE0NzA4ODgxMDcifQ.Wt8ZTdS0UexaLm6QUSExu5xKXmUFP-pLmzjj3vJ4VDM")
    @PUT("/user/my")
    fun reqEditNickName(@Body reqSignIn: ReqEditNickName): Single<ResUser>

    @Headers("Authorization:eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNTk5ODU1NzQwLCJleHAiOjE2MDI0NDc3NDAsInVpZCI6IktBS0FPXzE0NzA4ODgxMDcifQ.Wt8ZTdS0UexaLm6QUSExu5xKXmUFP-pLmzjj3vJ4VDM")
    @GET("/user/my")
    fun reqGetNickName(): Single<ResUser>

}
