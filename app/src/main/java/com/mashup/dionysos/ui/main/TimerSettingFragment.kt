package com.mashup.dionysos.ui.main

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mashup.dionysos.BR
import com.mashup.dionysos.R
import com.mashup.dionysos.base.fragment.BaseFragment
import com.mashup.dionysos.databinding.FragmentTimerSettingBinding
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

        Log.e("onActivityCreated", "  TimerSettingFragment")

        timeViewModel.timeDataModel.observe(this, Observer { it ->
            if (!it.increase && timeViewModel.timerClickable.value!!.clickable) {
                Log.e("timeDataModel", "it $it")
                bottomSheet.show(childFragmentManager, "")
            }
        })
        timeViewModel.popFragment.observe(this, Observer { it ->
            if (it) {
                timeViewModel.popFragment.value = false
                parentFragmentManager.beginTransaction().remove(this).commit()
                parentFragmentManager.popBackStack()
            }
        })
        timeViewModel.timerSettingHours.observe(this, Observer { it ->
            if (it != null && it != "") {
                if (it.toInt() > 24) {
                    timeViewModel.timerSettingHours.value = 0.toString()
                } else if (it.toInt() != 0)
                    timeViewModel.timerClickable.value = TimerSetting(true)
            }
        })
        timeViewModel.timerSettingMin.observe(this, Observer { it ->
            if (it != null && it != "") {
                if (it.toInt() != 0)
                    timeViewModel.timerClickable.value = TimerSetting(true)
            }
        })
        timeViewModel.timerSettingSec.observe(this, Observer { it ->
            if (it != null && it != "") {
                if (it.toInt() != 0)
                    timeViewModel.timerClickable.value = TimerSetting(true)
            }
        })
    }

    override fun onDestroy() {
        timeViewModel.timerSettingNull()
        timeViewModel.timerClickable.value = TimerSetting(false)
        super.onDestroy()
    }

    companion object {
        @JvmStatic
        fun newInstance(): TimerSettingFragment {
            return TimerSettingFragment()
        }
    }
}
