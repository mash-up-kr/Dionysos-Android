package com.mashup.dionysos.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mashup.dionysos.R
import com.mashup.dionysos.base.activity.BaseActivity
import com.mashup.dionysos.databinding.ActivityMainBinding
import com.mashup.dionysos.ui.mypage.MyPageEditActivity
import com.mashup.dionysos.ui.mypage.MyPageFragment
import com.mashup.dionysos.ui.ranking.RankingFragment
import com.mashup.dionysos.ui.setting.SettingActivity


class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    companion object {
        private const val TAG = "MAIN_ACTIVITY"
    }

    lateinit var timeViewModel: TimeViewModel
    private val mainHomeFragment = MainHomeFragment()
    private val myPageFragment = MyPageFragment()
    private val rankingFragment = RankingFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initMainDataBinding()
        Log.e(TAG, ":  onCreateView")

        val repository = getMogakgongApi()
        timeViewModel.repository = repository
        timeViewModel.changeFragment.observe(this, Observer { it ->
            Log.e(TAG, ":  changeFragment $it")

            timeViewModel.newFragment = true
            when (it) {
                1 -> {
                    val fragmentManager = supportFragmentManager
                    val transaction = fragmentManager.beginTransaction()
                    transaction.replace(R.id.fragmentMainTap, rankingFragment).commit()
                }
                2 -> {
                    val fragmentManager = supportFragmentManager
                    val transaction = fragmentManager.beginTransaction()
                    transaction.replace(R.id.fragmentMainTap, mainHomeFragment).commit()
                }
                3 -> {
                    val fragmentManager = supportFragmentManager
                    val transaction = fragmentManager.beginTransaction()
                    transaction.replace(R.id.fragmentMainTap, myPageFragment).commit()
                }
                4 -> {
                    val intent = Intent(this, MyPageEditActivity::class.java)
                    startActivity(intent)
                }
                5 -> {
                    val intent = Intent(this, SettingActivity::class.java)
                    startActivity(intent)
                }
            }
        })
    }

    override fun onRestart() {
        Log.e(TAG, ":  onCreateView")
        super.onRestart()
    }

    override fun onResume() {
        Log.e(TAG, ":  onCreateView")
        super.onResume()
    }

    override fun onDestroy() {
        Log.e(TAG, ":  onCreateView")
        super.onDestroy()
    }

    private fun initMainDataBinding() {
        val viewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        timeViewModel = ViewModelProvider(this, viewModelFactory).get(TimeViewModel::class.java)
        binding.timeVM = timeViewModel
    }

}