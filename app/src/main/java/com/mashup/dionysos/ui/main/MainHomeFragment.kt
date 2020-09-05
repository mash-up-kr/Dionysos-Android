package com.mashup.dionysos.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mashup.dionysos.BR
import com.mashup.dionysos.R
import com.mashup.dionysos.base.fragment.BaseFragment
import com.mashup.dionysos.databinding.FragmentMainHomeBinding


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
            if (!timeViewModel.newFragment) {
                when (it) {
                    TimeViewModel.SelectFragment.Setting -> {
                        replaceFragment(TimerSettingFragment.newInstance())
                    }
                    TimeViewModel.SelectFragment.BottomSheet -> {
                        bottomSheet.show(fragmentManager!!, "exampleBottomSheet")
                    }
                }
            } else {
                timeViewModel.newFragment = false
            }
        })

        timeViewModel.timeLapse.observe(this, Observer { it ->
            if (timeViewModel.fragmentChange.value == TimeViewModel.SelectFragment.TIMER) {
                when (it) {
                    TimeViewModel.SelectTimeLapse.YEAH, TimeViewModel.SelectTimeLapse.NOPE -> {
                        timeViewModel.popFragment.value = true
                    }
                }
            }
            if (it == TimeViewModel.SelectTimeLapse.YEAH) {
                Log.e("fragmentChange", "카메라")
                timeViewModel.timeLapse.value = TimeViewModel.SelectTimeLapse.DISMISS
            } else if (it == TimeViewModel.SelectTimeLapse.NOPE) {
                replaceFragment(TimeControlFragment.newInstance())
                timeViewModel.timeLapse.value = TimeViewModel.SelectTimeLapse.DISMISS
            }
        })
    }

    private fun replaceFragment(fragment: Fragment) {
        if (!fragment.isAdded) {
            timeViewModel.newFragment = true
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