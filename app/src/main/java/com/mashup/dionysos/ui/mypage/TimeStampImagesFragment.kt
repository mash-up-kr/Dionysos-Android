package com.mashup.dionysos.ui.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.mashup.dionysos.R
import com.mashup.dionysos.databinding.TimeStampImagesFragmentBinding
import com.mashup.dionysos.model.TimeStamp
import com.mashup.dionysos.base.fragment.BaseFragment
import com.mashup.dionysos.ui.mypage.adapter.TimeStampAdapter

class TimeStampImagesFragment :
    BaseFragment<TimeStampImagesFragmentBinding>(R.layout.time_stamp_images_fragment) {

    companion object {
        private const val TIME_STAMP_LIST_KEY = "timeStampListKey"
        fun newInstance(cellList: ArrayList<TimeStamp>): Fragment {
            val fragment = TimeStampImagesFragment()

            val args = Bundle()
            args.putParcelableArrayList(TIME_STAMP_LIST_KEY, cellList)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.recyclerview.adapter =
            TimeStampAdapter()
        arguments?.getParcelableArrayList<TimeStamp>(TIME_STAMP_LIST_KEY)?.run {
            (binding.recyclerview.adapter as TimeStampAdapter).submitList(this.toMutableList())
        }
    }
}
