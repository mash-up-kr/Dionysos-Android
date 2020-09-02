package com.mashup.dionysos.ui.ranking

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.tabs.TabLayoutMediator
import com.mashup.dionysos.R
import com.mashup.dionysos.databinding.RankingFragmentBinding
import com.mashup.dionysos.base.fragment.BaseFragment

class RankingFragment : BaseFragment<RankingFragmentBinding>(R.layout.ranking_fragment) {

    companion object {
        fun newInstance() = RankingFragment()
    }

    private val tabLayoutTextArray = arrayOf("day", "week", "month")

    private lateinit var viewModel: RankingViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RankingViewModel::class.java)

        binding.viewpager.adapter =
            RankingViewPagerAdapter(
                requireActivity()
            )
        TabLayoutMediator(binding.tabLayout, binding.viewpager) { tab, position ->
            tab.text = tabLayoutTextArray[position]
        }.attach()
    }

}
