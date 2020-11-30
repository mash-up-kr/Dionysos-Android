package com.mashup.dionysos.ui.ranking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mashup.dionysos.R
import com.mashup.dionysos.model.Ranking
import kotlinx.android.synthetic.main.statistics_graph_fragment.*

class RankingPageFragment : Fragment() {

    companion object {
        fun newInstance() = RankingPageFragment()
    }

    private lateinit var viewModel: RankingPageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.ranking_page_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RankingPageViewModel::class.java)
        // TODO: Use the ViewModel

        recyclerview.adapter = RankingAdapter()
        val list = listOf(
            Ranking(1, 154, "원숭이가나무에서떨어져", null, 10, true),
            Ranking(
                0, 1, "초코파이 ",
                "https://avatars3.githubusercontent.com/u/18034145?s=400&u=64f9f59411b56aa1d26c78fb51e1bcd4e96de91e&v=4",
                99999990
            ),
            Ranking(
                1, 2, "이재르시 ",
                "https://epicbox.co.kr/web/product/big/201707/4114_shop1_973730.jpg",
                8888880
            ),
            Ranking(
                2, 4, "다람쥐  ",
                "https://avatars1.githubusercontent.com/u/16275188?s=400&u=0aa463ab1e9a076e2828dda7e02f94ff168ea46a&v=4",
                777770
            ),
            Ranking(
                3,
                5,
                "부엉부엉",
                "https://avatars2.githubusercontent.com/u/39197978?s=460&u=e1ea7073fc9ff683dd31ce847f5147022059ecad&v=4",
                776660
            ),
            Ranking(
                4,
                6,
                "초코파이 ",
                "https://avatars3.githubusercontent.com/u/18034145?s=400&u=64f9f59411b56aa1d26c78fb51e1bcd4e96de91e&v=4",
                9990
            ),
            Ranking(
                5,
                7,
                "이재르시 ",
                "https://epicbox.co.kr/web/product/big/201707/4114_shop1_973730.jpg",
                8880
            )
        )
        (recyclerview.adapter as RankingAdapter).submitList(list.toMutableList())
    }

}
