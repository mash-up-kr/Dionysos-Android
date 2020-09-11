package com.mashup.dionysos.ui.setting

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.mashup.dionysos.BR
import com.mashup.dionysos.R
import com.mashup.dionysos.databinding.RankingFragmentBinding
import com.mashup.dionysos.base.fragment.BaseFragment
import com.mashup.dionysos.databinding.SettingFragmentBinding
import com.mashup.dionysos.databinding.SettingMadeByFragmentBinding
import com.mashup.dionysos.databinding.SettingWebFragmentBinding
import com.mashup.dionysos.ui.main.TimeViewModel
import com.mashup.dionysos.ui.mypage.MyPageViewModel
import com.mashup.dionysos.ui.ranking.RankingViewModel
import kotlinx.android.synthetic.main.setting_web_fragment.*

class SettingMadeByFragment : BaseFragment<SettingMadeByFragmentBinding>(R.layout.setting_made_by_fragment) {

    companion object {
        fun newInstance() = SettingMadeByFragment()
    }
    private lateinit var viewModel: SettingViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModelFactory =
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        viewModel = ViewModelProvider(activity!!, viewModelFactory).get(SettingViewModel::class.java)
        binding.setVariable(BR.settingVM, viewModel)
    }
}
