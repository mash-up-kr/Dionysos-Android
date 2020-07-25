package com.mashup.dionysos.ui.mypage

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.mashup.dionysos.R
import com.mashup.dionysos.databinding.MyPageFragmentBinding
import com.mashup.dionysos.ui.BaseFragment
import com.mashup.dionysos.ui.mypage.viewpager.MyPageViewPagerAdapter

class MyPageFragment : BaseFragment<MyPageFragmentBinding>(R.layout.my_page_fragment) {

    companion object {
        fun newInstance() = MyPageFragment()
    }

    private lateinit var viewModel: MyPageViewModel

    private val tabLayoutTextArray = arrayOf("통계", "타임스탬프")

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyPageViewModel::class.java)
        // TODO: Use the ViewModel

        binding.viewpager.adapter =
            MyPageViewPagerAdapter(
                requireActivity()
            )
        binding.viewpager.offscreenPageLimit = 2
        TabLayoutMediator(binding.tabLayout, binding.viewpager) { tab, position ->
            tab.text = tabLayoutTextArray[position]
        }.attach()
    }
}
