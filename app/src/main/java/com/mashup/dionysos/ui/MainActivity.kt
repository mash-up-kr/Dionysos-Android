package com.mashup.dionysos.ui

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.exoplayer2.util.Log
import com.mashup.dionysos.R
import com.mashup.dionysos.databinding.ActivityMainBinding
import com.mashup.dionysos.fragment.MainHomeFragment
import com.mashup.dionysos.ui.mypage.MyPageFragment
import com.mashup.dionysos.ui.ranking.RankingFragment
import com.mashup.dionysos.viewmodel.TimeViewModel


class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    lateinit var timeViewModel: TimeViewModel

    private val mainHomeFragment = MainHomeFragment()
    private val myPageFragment = MyPageFragment()
    private val rankingFragment = RankingFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initMainDataBinding()



        timeViewModel.changeFagment.observe(this, Observer { it ->
            Log.e("1322","xddd")
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
            }
        })
    }

    private fun initMainDataBinding() {
        val viewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        timeViewModel = ViewModelProvider(this, viewModelFactory).get(TimeViewModel::class.java)
        binding.timeVM = timeViewModel
    }

}