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

    @Headers("Authorization:eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNTk5NzMyNTk2LCJleHAiOjE2MDIzMjQ1OTYsInVpZCI6IktBS0FPXzIyMjMifQ.7p9zIobula6Ue6amNS0iS1wSFWs0f-2hs9pLOSxs304")
    @PUT("/user/my")
    fun reqEditNickName(@Body reqSignIn: ReqEditNickName): Single<ResUser>

    @Headers("Authorization:eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNTk5NzMyNTk2LCJleHAiOjE2MDIzMjQ1OTYsInVpZCI6IktBS0FPXzIyMjMifQ.7p9zIobula6Ue6amNS0iS1wSFWs0f-2hs9pLOSxs304")
    @GET("/user/my")
    fun reqGetNickName(): Single<ResUser>

}
