package com.mashup.dionysos.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mashup.dionysos.BR
import com.mashup.dionysos.BottomSheetDialog
import com.mashup.dionysos.R
import com.mashup.dionysos.databinding.FragmentTimerSettingBinding
import com.mashup.dionysos.model.TimerSetting
import com.mashup.dionysos.viewmodel.TimeViewModel

class TimerSettingFragment :
        BaseFragment<FragmentTimerSettingBinding>(R.layout.fragment_timer_setting) {

    lateinit var timeViewModel: TimeViewModel

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


        timeViewModel._timerSettingHours.observe(this, Observer { it ->
            if (it.toInt() > 24) {
                timeViewModel._timerSettingHours.value = 0.toString()
            } else if (it.toInt() != 0)
                timeViewModel.timerClickable.value = TimerSetting(true)
        })
        timeViewModel._timerSettingMin.observe(this, Observer { it ->
            if (it.toInt() != 0)
                timeViewModel.timerClickable.value = TimerSetting(true)

        })
        timeViewModel._timerSettingSec.observe(this, Observer { it ->
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

    override fun onDetach() {
        super.onDetach()
    }

    companion object {
        @JvmStatic
        fun newInstance(): TimerSettingFragment {
            return TimerSettingFragment()
        }
    }
}
