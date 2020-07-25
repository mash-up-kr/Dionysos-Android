package com.mashup.dionysos.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mashup.dionysos.R
import com.mashup.dionysos.ui.mypage.MyPageFragment

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MyPageFragment.newInstance())
                .commitNow()
        }
    }
}
