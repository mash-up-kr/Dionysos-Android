package com.mashup.dionysos.ui.mypage

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mashup.dionysos.R
import com.mashup.dionysos.databinding.StatisticsFragmentBinding
import com.mashup.dionysos.model.StatisticsGraph
import com.mashup.dionysos.ui.BaseFragment
import com.mashup.dionysos.ui.mypage.viewpager.StatisticsViewPagerAdapter

class StatisticsFragment : BaseFragment<StatisticsFragmentBinding>(R.layout.statistics_fragment) {

    companion object {
        fun newInstance() = StatisticsFragment()
    }

    private lateinit var viewModel: StatisticsViewModel

    private val tabLayoutTextArray = arrayOf("8", "9", "10", "11")

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(StatisticsViewModel::class.java)
        // TODO: Use the ViewModel

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
            StatisticsGraph("0", 0),
            StatisticsGraph("1", 1),
            StatisticsGraph("2", 2),
            StatisticsGraph("3", 3),
            StatisticsGraph("4", 4),
            StatisticsGraph("5", 5),
            StatisticsGraph("6", 6),
            StatisticsGraph("7", 7),
            StatisticsGraph("8", 8),
            StatisticsGraph("9", 9),
            StatisticsGraph("10", 10),
            StatisticsGraph("0", 0),
            StatisticsGraph("1", 1),
            StatisticsGraph("2", 2),
            StatisticsGraph("3", 3),
            StatisticsGraph("4", 4),
            StatisticsGraph("5", 5),
            StatisticsGraph("6", 6),
            StatisticsGraph("7", 7),
            StatisticsGraph("8", 8),
            StatisticsGraph("9", 9),
            StatisticsGraph("10", 10),
            StatisticsGraph("0", 0),
            StatisticsGraph("1", 1),
            StatisticsGraph("2", 2),
            StatisticsGraph("3", 3),
            StatisticsGraph("4", 4),
            StatisticsGraph("5", 5),
            StatisticsGraph("6", 6),
            StatisticsGraph("7", 7),
            StatisticsGraph("8", 8),
            StatisticsGraph("9", 9),
            StatisticsGraph("10", 10)
        )

        val secondList = arrayListOf(
            StatisticsGraph("0", 5),
            StatisticsGraph("1", 1),
            StatisticsGraph("2", 4),
            StatisticsGraph("3", 3),
            StatisticsGraph("4", 4),
            StatisticsGraph("5", 1),
            StatisticsGraph("6", 6),
            StatisticsGraph("7", 7),
            StatisticsGraph("8", 8),
            StatisticsGraph("9", 2),
            StatisticsGraph("10", 3),
            StatisticsGraph("0", 0),
            StatisticsGraph("1", 1),
            StatisticsGraph("2", 2),
            StatisticsGraph("3", 3),
            StatisticsGraph("4", 4),
            StatisticsGraph("5", 5),
            StatisticsGraph("6", 6),
            StatisticsGraph("7", 7),
            StatisticsGraph("8", 8),
            StatisticsGraph("9", 9),
            StatisticsGraph("10", 10),
            StatisticsGraph("0", 0),
            StatisticsGraph("1", 1),
            StatisticsGraph("2", 2),
            StatisticsGraph("3", 3),
            StatisticsGraph("4", 4),
            StatisticsGraph("5", 5),
            StatisticsGraph("6", 6),
            StatisticsGraph("7", 7),
            StatisticsGraph("8", 8),
            StatisticsGraph("9", 9),
            StatisticsGraph("10", 10)
        )

        val thirdList = arrayListOf(
            StatisticsGraph("0", 0),
            StatisticsGraph("1", 1),
            StatisticsGraph("2", 2),
            StatisticsGraph("3", 3),
            StatisticsGraph("4", 4),
            StatisticsGraph("5", 5),
            StatisticsGraph("6", 6),
            StatisticsGraph("7", 7),
            StatisticsGraph("8", 8),
            StatisticsGraph("9", 9),
            StatisticsGraph("10", 10),
            StatisticsGraph("0", 0),
            StatisticsGraph("1", 1),
            StatisticsGraph("2", 2),
            StatisticsGraph("3", 3),
            StatisticsGraph("4", 4),
            StatisticsGraph("5", 5),
            StatisticsGraph("6", 6),
            StatisticsGraph("7", 7),
            StatisticsGraph("8", 8),
            StatisticsGraph("9", 9),
            StatisticsGraph("10", 10)
        )

        val firthList = arrayListOf(
            StatisticsGraph("0", 0),
            StatisticsGraph("1", 1),
            StatisticsGraph("2", 2),
            StatisticsGraph("3", 3),
            StatisticsGraph("4", 4),
            StatisticsGraph("5", 5),
            StatisticsGraph("0", 0),
            StatisticsGraph("1", 1),
            StatisticsGraph("2", 2),
            StatisticsGraph("3", 3),
            StatisticsGraph("4", 4),
            StatisticsGraph("5", 5),
            StatisticsGraph("6", 6),
            StatisticsGraph("7", 7),
            StatisticsGraph("8", 8),
            StatisticsGraph("9", 9),
            StatisticsGraph("10", 10),
            StatisticsGraph("6", 6),
            StatisticsGraph("7", 7),
            StatisticsGraph("8", 8),
            StatisticsGraph("9", 9),
            StatisticsGraph("10", 10)
        )

        val lists = arrayListOf(firstList, secondList, thirdList, firstList)
        binding.viewpager.adapter =
            StatisticsViewPagerAdapter(
                requireActivity(),
                lists
            )
        TabLayoutMediator(binding.tabLayout, binding.viewpager) { tab, position ->
            tab.text = tabLayoutTextArray[position]
        }.attach()
    }

}
