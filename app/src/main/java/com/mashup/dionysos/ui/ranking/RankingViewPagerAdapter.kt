package com.mashup.dionysos.ui.ranking

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class RankingViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> RankingPageFragment.newInstance()
            1 -> RankingPageFragment.newInstance()
            else -> RankingPageFragment.newInstance()
        }
    }

    override fun getItemCount(): Int = 3
}