package com.mashup.dionysos.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.facebook.CallbackManager
import com.kakao.auth.AuthType
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.exception.KakaoException
import com.mashup.dionysos.R
import com.mashup.dionysos.facebooklogin.LoginCallback
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*


class LoginActivity : AppCompatActivity() {

    var session: Session? = null

    private var mLoginCallback: LoginCallback? = null
    private var mCallbackManager: CallbackManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()
        mogackgong_ko_text.setOnClickListener {
            redirectNicknameActivity()
        }

    }

    private fun init() {
        initSession()
        setListeners()
    }

    private fun initSession() {
        // 세션 콜백 등록
        session = Session.getCurrentSession()
        session?.addCallback(sessionCallback)
        //토큰 만료시 갱신 시켜줌
        Session.getCurrentSession().checkAndImplicitOpen()

        // facebook
        mCallbackManager = CallbackManager.Factory.create()
        mLoginCallback = LoginCallback()
    }

    private fun setListeners() {
        kakao_login_btn.setOnClickListener {
            session!!.open(AuthType.KAKAO_ACCOUNT, this)
        }
        facebook_login_btn.setOnClickListener {
            facebook_login_btn.setReadPermissions(Arrays.asList("public_profile", "email"));
            facebook_login_btn.registerCallback(mCallbackManager, mLoginCallback);
        }
    }

    // 세션 콜백 구현
    private val sessionCallback: ISessionCallback = object : ISessionCallback {
        // 로그인에 성공한 상태
        override fun onSessionOpened() {
            Log.i("KAKAO_SESSION", "로그인 성공")
            requestMe()
        }

        // 로그인에 실패한 상태
        override fun onSessionOpenFailed(exception: KakaoException) {
            //Log.e("KAKAO_SESSION", "로그인 실패", exception);
            Log.e("SessionCallback :: ", "onSessionOpenFailed : " + exception.message)
        }

        // 사용자 정보 요청
        fun requestMe() {
            // 사용자정보 요청 결과에 대한 Callback
            UserManagement.getInstance().me(object : MeV2ResponseCallback() {
                // 세션 오픈 실패. 세션이 삭제된 경우
                override fun onSessionClosed(errorResult: ErrorResult) {
                    Log.e("KAKAO_API", "세션이 닫혀 있음: $errorResult")
                }

                // 사용자 정보 요청 실패
                override fun onFailure(errorResult: ErrorResult) {
                    Log.e("KAKAO_API", "사용자 정보 요청 실패: $errorResult")
                }

                // 사용자정보 요청에 성공한 경우,
                override fun onSuccess(result: MeV2Response) {
                    val uid = java.lang.Long.toString(result.id)
                    Log.i("KAKAO_API", "사용자 아이디: $uid")
                    redirectNicknameActivity()
                }
            })
        }
    }

    private fun redirectNicknameActivity() {
        val intent = Intent(this, NicknameActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        // 세션 콜백 삭제
        Session.getCurrentSession().removeCallback(sessionCallback)
    }

    override fun onActivityResult(
            requestCode: Int,
            resultCode: Int,
            data: Intent?
    ) {
        // 카카오톡|스토리 간편로그인 실행 결과를 받아서 SDK로 전달
        if (Session.getCurrentSession()
                        .handleActivityResult(requestCode, resultCode, data)
        ) {
            return
        }

        //mCallbackManager.onActivityResult(requestCode, resultCode, data)

        super.onActivityResult(requestCode, resultCode, data)
    }

    /*
    //해시키 얻는 코드
    private fun getHashKey() {
        var packageInfo: PackageInfo? = null
        try {
            packageInfo =
                packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        if (packageInfo == null) Log.e("KeyHash", "KeyHash:null")
        for (signature in packageInfo!!.signatures) {
            try {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d(
                    "KeyHash",
                    Base64.encodeToString(md.digest(), Base64.DEFAULT)
                )
            } catch (e: NoSuchAlgorithmException) {
                Log.e(
                    "KeyHash",
                    "Unable to get MessageDigest. signature=$signature",
                    e
                )
            }
        }
    }
    */
}
