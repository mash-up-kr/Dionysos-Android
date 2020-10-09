package com.mashup.dionysos

import android.app.Activity
import android.app.Application
import com.kakao.auth.KakaoSDK
import com.mashup.dionysos.di.apiModule
import com.mashup.dionysos.kakaologin.KakaoSDKAdapter
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class DyonysosApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(apiModule)
        }
        instance = this
        // Kakao Sdk 초기화
        KakaoSDK.init(KakaoSDKAdapter())
    }

    /**
     * 애플리케이션 종료시 singleton 어플리케이션 객체 초기화한다.
     */
    override fun onTerminate() {
        super.onTerminate()
        instance = null
    }

    companion object {
        private var instance: DyonysosApp? = null
        private val currentActivity: Activity? = null

        /**
         * singleton 애플리케이션 객체를 얻는다.
         *
         * @return singleton 애플리케이션 객체
         */
        val globalApplicationContext: DyonysosApp
            get() {
                checkNotNull(instance) { "This Application does not inherit com.kakao.GlobalApplication" }
                return instance!!
            }
    }
}
