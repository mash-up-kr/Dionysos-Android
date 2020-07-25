package com.mashup.dionysos.ui.mypage.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mashup.dionysos.model.StatisticsGraph
import com.mashup.dionysos.ui.mypage.StatisticsGraphFragment

class StatisticsViewPagerAdapter(
    fa: FragmentActivity,
    private val lists: ArrayList<ArrayList<StatisticsGraph>>
) : FragmentStateAdapter(fa) {
    override fun createFragment(position: Int): Fragment {
        return StatisticsGraphFragment.newInstance(
            lists[position]
        )
    }

    override fun getItemCount(): Int = lists.size
}