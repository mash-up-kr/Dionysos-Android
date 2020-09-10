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
            Ranking(5, 154, "원숭원숭", "", 10, true),
            Ranking(0, 4, "하이", "", 90),
            Ranking(1, 5, "하이2", "", 80),
            Ranking(2, 6, "하이3", "", 70)
        )
        (recyclerview.adapter as RankingAdapter).submitList(list.toMutableList())
    }

}
