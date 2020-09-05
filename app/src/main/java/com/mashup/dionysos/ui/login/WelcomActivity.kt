package com.mashup.dionysos.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.mashup.dionysos.R
import com.mashup.dionysos.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_welcom.*

class WelcomActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcom)

        Glide.with(this).load(R.drawable.animation_signup).into(gif_img)

        gif_img.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
