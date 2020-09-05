package com.mashup.dionysos.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.mashup.dionysos.R
import com.mashup.dionysos.api.MogakgongRetrofit
import com.mashup.dionysos.api.dto.ReqNicknameCheck
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_nickname.*
import retrofit2.adapter.rxjava3.HttpException

class NicknameActivity : AppCompatActivity() {

    var nickname = ""
    private val repository = MogakgongRetrofit.getService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nickname)
        setListeners()
        nickname_edit_text.afterTextChanged { checkNickname(nickname_edit_text.text.toString()) }
    }

    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
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
            val intent = Intent(this, WelcomActivity::class.java)
            nickname = nickname_edit_text.text.toString()
            startActivity(intent)
            finish()
        }
    }

    private fun checkNickname(text:String) {
        repository.reqNicknameCheck(ReqNicknameCheck(text))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                ans_text.text = resources.getText(R.string.nickname_great)
                ans_text.setTextColor(ContextCompat.getColor(this, R.color.azure))
                ok_btn.isSelected = true
            }, { e ->
                e.printStackTrace()
                ans_text.text = resources.getText(R.string.nickname_um)
                ans_text.setTextColor(ContextCompat.getColor(this, R.color.coral_pink))
                ok_btn.isSelected = false
            })
    }
}
