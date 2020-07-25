package com.mashup.dionysos.ui.mypage.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mashup.dionysos.model.TimeStamp
import com.mashup.dionysos.ui.mypage.TimeStampImagesFragment

class TimeStampViewPagerAdapter(
    fa: FragmentActivity,
    private val lists: ArrayList<ArrayList<TimeStamp>>
) : FragmentStateAdapter(fa) {
    override fun createFragment(position: Int): Fragment {
        return TimeStampImagesFragment.newInstance(
            lists[position]
        )
    }

    override fun getItemCount(): Int = lists.size
}