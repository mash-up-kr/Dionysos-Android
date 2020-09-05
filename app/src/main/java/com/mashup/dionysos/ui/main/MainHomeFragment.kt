package com.mashup.dionysos.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mashup.dionysos.BR
import com.mashup.dionysos.R
import com.mashup.dionysos.databinding.FragmentMainHomeBinding
import com.mashup.dionysos.base.fragment.BaseFragment


class MainHomeFragment :
        BaseFragment<FragmentMainHomeBinding>(R.layout.fragment_main_home) {

    private lateinit var timeViewModel: TimeViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModelFactory =
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        val bottomSheet = BottomSheetDialog()

        timeViewModel = ViewModelProvider(activity!!, viewModelFactory).get(TimeViewModel::class.java)
        binding.setVariable(BR.timeVM, timeViewModel)
        bottomSheet.timeViewModel = timeViewModel

        timeViewModel.fragmentChange.observe(this, Observer { it ->
                Log.e("fragmentChange", it.toString())
                if (it) {
                    replaceFragment(TimerSettingFragment.newInstance())
                } else {
                    bottomSheet.show(fragmentManager!!, "exampleBottomSheet")
                }
        })
        timeViewModel.timeLaps.observe(this, Observer { it ->
                Log.e("timeLaps", "$it")
                if (it) {
                    Log.e("fragmentChange", "카메라")
                } else {
                    replaceFragment(TimeControlFragment.newInstance())
                }
        })
    }

    private fun replaceFragment(fragment: Fragment) {
        if (!fragment.isAdded) {
            timeViewModel.showMainTabBar.value = false
            val transaction = fragmentManager!!.beginTransaction()
            transaction.addToBackStack(null)
            transaction.replace(R.id.fragment_time_control, fragment).commit();

        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): MainHomeFragment {
            return MainHomeFragment()
        }
    }
}