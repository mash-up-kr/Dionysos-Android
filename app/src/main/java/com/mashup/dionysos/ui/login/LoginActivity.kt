package com.mashup.dionysos.ui.login

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.kakao.auth.AuthType
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.exception.KakaoException
import com.mashup.dionysos.R
import com.mashup.dionysos.api.MogakgongRetrofit
import com.mashup.dionysos.api.dto.Provider
import com.mashup.dionysos.api.dto.ReqSignIn
import com.mashup.dionysos.facebooklogin.LoginCallback
import com.mashup.dionysos.ui.main.MainActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "LOGIN_ACTIVITY"
        private const val sharedPrefFile = "app_preferences"
        private const val jwt = "jwt"
    }
    private val REQUEST_CODE =1
    private lateinit var mPreferences: SharedPreferences

    var session: Session? = null
    var userId = "guest"
    var provider = Provider.GUEST.value

    private var mLoginCallback: LoginCallback? = null
    private var mCallbackManager: CallbackManager? = null
    private val repository = MogakgongRetrofit().getService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        init()
        mogackgong_ko_text.setOnClickListener {
            redirectNicknameActivity()
        }

        guest_login_btn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,  Manifest.permission.WRITE_EXTERNAL_STORAGE )
            , REQUEST_CODE)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                getCurrentLocation()
            } else {
                Toast.makeText(this, "no_permission_msg", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun init() {
        initSession()
        setListeners()
        val keyHash = com.kakao.util.helper.Utility.getKeyHash(this /* context */)
        Log.d("loloss", keyHash)
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
            facebook_login_btn.registerCallback(mCallbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    val accessToken = result.accessToken
                    provider = Provider.FACEBOOK.value
                    userId = accessToken.userId
                    redirectNicknameActivity()
                }

                override fun onCancel() {
                    //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onError(error: FacebookException?) {
                    //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            })
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
                    val uid = result.id.toString()
                    provider = Provider.KAKAO.value
                    userId = uid
                    redirectNicknameActivity()
                }
            })
        }
    }

    private fun redirectNicknameActivity() {
        signIn()
    }

    private fun signIn() {
        repository.reqSignIn(ReqSignIn(provider, userId))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.e(TAG, "success signIn $it")
                val intent = Intent(this, MainActivity::class.java)
                val preferencesEditor: SharedPreferences.Editor = mPreferences.edit()
                preferencesEditor.putString(jwt, it.result.jwt)
                preferencesEditor.apply()
                startActivity(intent)
                finish()
            }, { e ->
                e.printStackTrace()
                val intent = Intent(this, NicknameActivity::class.java)
                intent.putExtra("userId", userId)
                intent.putExtra("provider", provider)
                startActivity(intent)
                finish()
            })
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

        //Log.d("loloss", "$requestCode  $resultCode")
        mCallbackManager?.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    /*
    //해시키 얻는 코드
    터미널에 `keytool -exportcert -alias androiddebugkey -keystore ~/.android/debug.keystore -storepass android -keypass android | openssl sha1 -binary | openssl base64`

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
