package com.mashup.dionysos.ui.mypage

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.mashup.dionysos.BR
import com.mashup.dionysos.R
import com.mashup.dionysos.base.fragment.BaseFragment
import com.mashup.dionysos.databinding.MyPageFragmentBinding
import com.mashup.dionysos.ui.main.TimeViewModel
import com.mashup.dionysos.ui.mypage.viewpager.MyPageViewPagerAdapter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MyPageFragment : BaseFragment<MyPageFragmentBinding>(R.layout.my_page_fragment) {

    companion object {
        fun newInstance() = MyPageFragment()
    }

    private lateinit var myPageViewModel: MyPageViewModel
    private lateinit var timeViewModel: TimeViewModel

    private val tabLayoutTextArray = arrayOf("통계", "타임스탬프")

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModelFactory =
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        myPageViewModel = ViewModelProvider(this, viewModelFactory).get(MyPageViewModel::class.java)
        timeViewModel =
            ViewModelProvider(activity!!, viewModelFactory).get(TimeViewModel::class.java)
        binding.setVariable(BR.timeVM, timeViewModel)
        binding.setVariable(BR.mypageVM, myPageViewModel)

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

    override fun onResume() {
        editNickName()
        super.onResume()
    }

    private fun editNickName() {
        repository.reqGetNickName()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.e("editNickName", "success $it")
                myPageViewModel.userInfo.value = it.result
            }, { e ->
                e.printStackTrace()
            })
    }
}
