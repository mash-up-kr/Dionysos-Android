package com.mashup.dionysos.ui.mypage

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mashup.dionysos.R
import com.mashup.dionysos.databinding.TimeStampFragmentBinding
import com.mashup.dionysos.model.TimeStamp
import com.mashup.dionysos.base.fragment.BaseFragment
import com.mashup.dionysos.ui.mypage.viewpager.TimeStampViewPagerAdapter

class TimeStampFragment : BaseFragment<TimeStampFragmentBinding>(R.layout.time_stamp_fragment) {

    companion object {
        fun newInstance() = TimeStampFragment()
    }

    private lateinit var viewModel: TimeStampViewModel

    private val tabLayoutTextArray = arrayOf("8", "9", "10", "11")

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TimeStampViewModel::class.java)

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                tab.text = tabLayoutTextArray[tab.position]
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                tab.text = "2020.9"
            }

        })

        val firstList = arrayListOf(
            TimeStamp(0, "0"),
            TimeStamp(1, "0"),
            TimeStamp(2, "0"),
            TimeStamp(3, "0"),
            TimeStamp(4, "0")
        )

        val secondList = arrayListOf(
            TimeStamp(0, "0"),
            TimeStamp(1, "0"),
            TimeStamp(2, "0"),
            TimeStamp(3, "0"),
            TimeStamp(4, "0")
        )

        val thirdList = arrayListOf(
            TimeStamp(0, "0"),
            TimeStamp(1, "0"),
            TimeStamp(2, "0"),
            TimeStamp(3, "0"),
            TimeStamp(4, "0")
        )

        val firthList = arrayListOf(
            TimeStamp(0, "0"),
            TimeStamp(1, "0"),
            TimeStamp(2, "0"),
            TimeStamp(3, "0"),
            TimeStamp(4, "0")
        )

        val lists = arrayListOf(firstList, secondList, thirdList, firthList)
        binding.viewpager.adapter =
            TimeStampViewPagerAdapter(
                requireActivity(),
                lists
            )
        TabLayoutMediator(binding.tabLayout, binding.viewpager) { tab, position ->
            tab.text = tabLayoutTextArray[position]
        }.attach()
    }

}
