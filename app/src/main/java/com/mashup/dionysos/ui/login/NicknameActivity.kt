package com.mashup.dionysos.ui.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.mashup.dionysos.R
import com.mashup.dionysos.api.MogakgongApi
import com.mashup.dionysos.api.dto.Provider
import com.mashup.dionysos.api.dto.ReqNicknameCheck
import com.mashup.dionysos.api.dto.ReqSignUp
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_nickname.*
import org.koin.android.ext.android.inject

class NicknameActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "LOGIN_ACTIVITY"
        private const val sharedPrefFile = "app_preferences"
        private const val jwt = "jwt"
    }

    private var userId:String? = null
    private var provider:String?= null
    private val repository: MogakgongApi by inject()
    private lateinit var mPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nickname)
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        setListeners()
        userId = intent.getStringExtra("userId")
        provider = intent.getStringExtra("provider")

        nickname_edit_text.afterTextChanged { checkNickname(nickname_edit_text.text.toString()) }
    }

    private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }
        })
    }

    private fun setListeners() {
        ok_btn.setOnClickListener {
            if (ok_btn.isSelected)
                signUp()
        }
    }
    private fun signUp() {
        val nickname = nickname_edit_text.text.toString()
        repository.reqSignUp(ReqSignUp(nickname,provider?:Provider.KAKAO.value,userId?:"guest1"))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.e(TAG, "success signUp $it")
                val intent = Intent(this, WelcomActivity::class.java)
                val preferencesEditor: SharedPreferences.Editor = mPreferences.edit()
                preferencesEditor.putString(jwt, it.result.jwt)
                preferencesEditor.apply()
                startActivity(intent)
                finish()
            }, { e ->
                e.printStackTrace()
                Log.e("signUp e", e.toString())
            })
    }

    private fun checkNickname(text:String) {
        repository.reqNicknameCheck(ReqNicknameCheck(text))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (ans_text.text != resources.getText(R.string.nickname_great)) {
                    ans_text.text = resources.getText(R.string.nickname_great)
                    ans_text.setTextColor(ContextCompat.getColor(this, R.color.azure))
                    ok_btn.isSelected = true
                }
            }, { e ->
                e.printStackTrace()
                ans_text.text = resources.getText(R.string.nickname_um)
                ans_text.setTextColor(ContextCompat.getColor(this, R.color.coral_pink))
                ok_btn.isSelected = false
            })
    }
}
