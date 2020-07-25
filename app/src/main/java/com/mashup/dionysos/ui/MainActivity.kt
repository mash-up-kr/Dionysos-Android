package com.mashup.dionysos.ui

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mashup.dionysos.R
import com.mashup.dionysos.databinding.ActivityMainBinding
import com.mashup.dionysos.fragment.MainHomeFragment
import com.mashup.dionysos.viewmodel.TimeViewModel


class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    lateinit var timeViewModel: TimeViewModel

    private val mainHomeFragment = MainHomeFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initMainDataBinding()


        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentMainTap, mainHomeFragment).commitAllowingStateLoss()

//        timeViewModel.fragmentChange.observe(this, Observer { it ->
//            val intent = Intent(applicationContext, TimePlayActivity::class.java)
//            startActivity(intent) //액티비티 띄우기
//        })


    }

    private fun initMainDataBinding() {
        val viewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        timeViewModel = ViewModelProvider(this, viewModelFactory).get(TimeViewModel::class.java)
        binding.timeVM = timeViewModel
    }


//    private fun replaceFragment(fragment: Fragment) {
//
//        val fragmentManager = supportFragmentManager
////        if (!fragment.isAdded)
////            fragmentManager.popBackStack()
//
//        val transaction = fragmentManager.beginTransaction()
//        if (mainViewModel.fragmentChangeToTimer.value!!)
//            transaction.addToBackStack(null)
//        transaction.replace(R.id.fragment_time_control, fragment).commitAllowingStateLoss();
//    }

}