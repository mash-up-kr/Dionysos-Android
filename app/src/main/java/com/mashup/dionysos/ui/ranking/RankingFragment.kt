package com.mashup.dionysos.ui.ranking

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.mashup.dionysos.R
import com.mashup.dionysos.base.fragment.BaseFragment
import com.mashup.dionysos.databinding.RankingFragmentBinding
import com.mashup.dionysos.ui.main.TimeViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class RankingFragment : BaseFragment<RankingFragmentBinding>(R.layout.ranking_fragment) {

    companion object {
        fun newInstance() = RankingFragment()
    }

    private val tabLayoutTextArray = arrayOf("day", "week", "month")

    private lateinit var viewModel: RankingViewModel
    private lateinit var timeViewModel: TimeViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RankingViewModel::class.java)
        val viewModelFactory =
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        timeViewModel =
            ViewModelProvider(activity!!, viewModelFactory).get(TimeViewModel::class.java)

        timeViewModel.repository.resRanking("day").subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.e("RankingFragment", "success $it")
            }, { e ->
                e.printStackTrace()
            })
        binding.viewpager.adapter =
            RankingViewPagerAdapter(
                requireActivity()
            )
        TabLayoutMediator(binding.tabLayout, binding.viewpager) { tab, position ->
            tab.text = tabLayoutTextArray[position]
        }.attach()
    }

}
