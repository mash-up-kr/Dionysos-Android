package com.mashup.dionysos.api.dto

//enum class Provider { KAKAO, NAVER, FACEBOOK, GOOGLE, APPLE, GUEST }
//enum class Provider2 {, NAVER }KAKAO

enum class Provider(val value: String) {
    KAKAO("KAKAO"), NAVER("NAVER"), FACEBOOK("FACEBOOK"),
    GOOGLE("GOOGLE"), APPLE("APPLE"), GUEST("GUEST")
}

