package com.mashup.dionysos.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mashup.dionysos.BR
import com.mashup.dionysos.R
import com.mashup.dionysos.databinding.FragmentTimerSettingBinding
import com.mashup.dionysos.base.fragment.BaseFragment
import com.mashup.dionysos.model.TimerSetting

class TimerSettingFragment :
        BaseFragment<FragmentTimerSettingBinding>(R.layout.fragment_timer_setting) {

    private lateinit var timeViewModel: TimeViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModelFactory =
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        val bottomSheet = BottomSheetDialog()
        timeViewModel =
                ViewModelProvider(activity!!, viewModelFactory).get(TimeViewModel::class.java)
        binding.setVariable(BR.timeVM, timeViewModel)

        timeViewModel.timeDataModel.observe(this, Observer { it ->
            if (!it.increase) {
                Log.e("timeDataModel", "it $it")
                bottomSheet.show(childFragmentManager, "")
            }
        })

        timeViewModel.timeLaps.observe(this, Observer { it ->
            if (it) {
                Log.e("fragmentChange", "카메라")
            } else {
                replaceFragment(TimeControlFragment())
            }
        })


        timeViewModel.timerSettingHours.observe(this, Observer { it ->
            if (it.toInt() > 24) {
                timeViewModel.timerSettingHours.value = 0.toString()
            } else if (it.toInt() != 0)
                timeViewModel.timerClickable.value = TimerSetting(true)
        })
        timeViewModel.timerSettingMin.observe(this, Observer { it ->
            if (it.toInt() != 0)
                timeViewModel.timerClickable.value = TimerSetting(true)

        })
        timeViewModel.timerSettingSec.observe(this, Observer { it ->
            if (it.toInt() != 0)
                timeViewModel.timerClickable.value = TimerSetting(true)

        })

        timeViewModel.isChild.observe(this, Observer { it ->
            parentFragmentManager.beginTransaction().remove(this).commit()
            parentFragmentManager.popBackStack()
        })

    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_time_setting, fragment).commit();
    }

    companion object {
        @JvmStatic
        fun newInstance(): TimerSettingFragment {
            return TimerSettingFragment()
        }
    }
}
