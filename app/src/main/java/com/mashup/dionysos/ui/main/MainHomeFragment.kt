package com.mashup.dionysos.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mashup.dionysos.BR
import com.mashup.dionysos.R
import com.mashup.dionysos.base.fragment.BaseFragment
import com.mashup.dionysos.base.viewmodel.BaseViewModel
import com.mashup.dionysos.databinding.FragmentMainHomeBinding
import com.mashup.dionysos.ui.timelapse.TimeLapseActivity


class MainHomeFragment :
        BaseFragment<FragmentMainHomeBinding>(R.layout.fragment_main_home) {

    private lateinit var timeViewModel: TimeViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModelFactory =
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        val bottomSheet = BottomSheetTimeLapse()
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
                    BaseViewModel.SelectBottomSheet.YEAH, BaseViewModel.SelectBottomSheet.NOPE -> {
                        timeViewModel.popFragment.value = true
                    }
                }
            }
            if (it == BaseViewModel.SelectBottomSheet.YEAH) {
                Log.e("fragmentChange", "카메라")
                timeViewModel.timeLapse.value = BaseViewModel.SelectBottomSheet.DISMISS
                val intent = Intent(activity, TimeLapseActivity::class.java)
                startActivity(intent)
            } else if (it == BaseViewModel.SelectBottomSheet.NOPE) {
                replaceFragment(TimeControlFragment.newInstance())
                timeViewModel.timeLapse.value = BaseViewModel.SelectBottomSheet.DISMISS
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