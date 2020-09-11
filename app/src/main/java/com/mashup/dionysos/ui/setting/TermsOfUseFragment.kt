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
import com.mashup.dionysos.R
import com.mashup.dionysos.databinding.RankingFragmentBinding
import com.mashup.dionysos.base.fragment.BaseFragment
import com.mashup.dionysos.databinding.SettingWebFragmentBinding
import com.mashup.dionysos.ui.ranking.RankingViewModel
import kotlinx.android.synthetic.main.setting_web_fragment.*

class TermsOfUseFragment : BaseFragment<SettingWebFragmentBinding>(R.layout.setting_web_fragment) {

    companion object {
        fun newInstance() = TermsOfUseFragment()
    }

    private lateinit var viewModel: SettingViewModel
    private val myUrl = "https://mogakgong.flycricket.io/privacy.html"

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SettingViewModel::class.java)
        web()
    }

    private fun web(){
        webView!!.settings.javaScriptEnabled = true //자바스크립트 허용
        webView!!.loadUrl(myUrl) //웹뷰 실행
        webView!!.webChromeClient = WebChromeClient() //웹뷰에 크롬 사용 허용//이 부분이 없으면 크롬에서 alert가 뜨지 않음
        webView!!.webViewClient =
            WebViewClientClass() //새창열기 없이 웹뷰 내에서 다시 열기//페이지 이동 원활히 하기위해 사용

    }

    private class WebViewClientClass : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView,
            url: String
        ): Boolean {
            Log.d("check URL", url)
            view.loadUrl(url)
            return true
        }
    }

}
