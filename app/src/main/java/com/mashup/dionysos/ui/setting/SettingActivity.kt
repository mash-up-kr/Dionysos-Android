package com.mashup.dionysos.ui.setting

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.LogoutResponseCallback
import com.mashup.dionysos.R
import com.mashup.dionysos.base.activity.BaseActivity
import com.mashup.dionysos.databinding.ActivitySettingBinding
import com.mashup.dionysos.ui.login.LoginActivity
import kotlinx.android.synthetic.main.setting_web_fragment.*


class SettingActivity : BaseActivity<ActivitySettingBinding>(R.layout.activity_setting) {
    companion object {
        private const val TAG = "SETTING_ACTIVITY"
    }

    private lateinit var settingViewModel: SettingViewModel
    private val settingFragment = SettingFragment()
    private val settingMadeByFragment = SettingMadeByFragment()
    private val termsOfUseFragment = TermsOfUseFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initMainDataBinding()
        Log.e(TAG, ":  onCreateView")

        settingViewModel.changeFragment.observe(this, Observer { it ->
            when (it) {
                -1 -> {
                    finish()
                }
                0 -> {
                    replaceFragment(settingFragment)
                }

                1 -> {
                    replaceFragment(termsOfUseFragment)
                }

                2 -> {
                    val email = Intent(Intent.ACTION_SEND);
                    email.type = "plain/text";
                    val address = "pointu341@gmail.com"
                    email.putExtra(Intent.EXTRA_EMAIL, address)
                    email.putExtra(Intent.EXTRA_SUBJECT, "모각공 문의드립니다. ")
                    email.putExtra(Intent.EXTRA_TEXT, "문제 발생 일시: \n 문의내용: \n")
                    startActivity(email);
                }
                3 -> {
                    replaceFragment(settingMadeByFragment)
                }
                5 -> {
                    UserManagement.getInstance().requestLogout(object : LogoutResponseCallback() {
                        override fun onSuccess(result: Long?) {
                            val preferencesEditor: SharedPreferences.Editor = mPreferences.edit()
                            preferencesEditor.clear()
                            preferencesEditor.apply()
                            Toast.makeText(this@SettingActivity
                                , "로그아웃 되었습니다.", Toast.LENGTH_LONG).show()
                            val intent = Intent(this@SettingActivity, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                            super.onSuccess(result)
                        }
                    })
                }
            }
        })

    }

    private fun replaceFragment(fragment: Fragment) {
        if (!fragment.isAdded) {
            val fragmentManager = supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.addToBackStack(null)
            transaction.replace(R.id.fragmentSetting, fragment).commit()
        }
    }

    private fun initMainDataBinding() {
        val viewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        settingViewModel =
            ViewModelProvider(this, viewModelFactory).get(SettingViewModel::class.java)
        binding.settingVM = settingViewModel
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean { //뒤로가기 버튼 이벤트
        if (keyCode == KeyEvent.KEYCODE_BACK && webView!!.canGoBack()) { //웹뷰에서 뒤로가기 버튼을 누르면 뒤로가짐
            webView!!.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}