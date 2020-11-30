package com.mashup.dionysos.ui.mypage.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mashup.dionysos.ui.mypage.StatisticsFragment
import com.mashup.dionysos.ui.mypage.TimeStampFragment

class MyPageViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> StatisticsFragment.newInstance()
            else -> TimeStampFragment.newInstance()
        }
    }

    override fun getItemCount(): Int = 2
}