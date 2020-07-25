package com.mashup.dionysos.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mashup.dionysos.R
import kotlinx.android.synthetic.main.activity_nickname.*

class NicknameActivity : AppCompatActivity() {

    var nickname = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nickname)

        setListeners()

    }

    private fun setListeners(){
        ok_btn.setOnClickListener{
            val intent = Intent(this, WelcomActivity::class.java)
            nickname = nickname_edit_text.text.toString()
            startActivity(intent)
            finish()
        }
    }
}
