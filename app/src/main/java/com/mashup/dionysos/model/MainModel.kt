package com.mashup.dionysos.model

import com.mashup.dionysos.R

data class MainModel(
        var ranking: Boolean = false,
        var home: Boolean = true,
        var myPage: Boolean = false
) {
    fun getRankingBtn(): Int {
        return if (ranking) R.drawable.ic_btn_ranking_on else R.drawable.ic_btn_ranking_off
    }
    fun getHomeBtn(): Int {
        return if (home) R.drawable.ic_btn_home_on else R.drawable.ic_btn_home_off
    }
    fun getMyPageBtn(): Int {
        return if (myPage) R.drawable.ic_btn_my_on else R.drawable.ic_btn_my_off
    }
}