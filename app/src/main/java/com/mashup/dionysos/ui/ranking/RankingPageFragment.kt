package com.mashup.dionysos.ui.ranking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.mashup.dionysos.R
import com.mashup.dionysos.model.StatisticsGraph
import com.mashup.dionysos.ui.mypage.StatisticsGraphFragment
import com.mashup.dionysos.ui.mypage.adapter.StatisticsGraphAdapter
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
        viewModel = ViewModelProviders.of(this).get(RankingPageViewModel::class.java)
        // TODO: Use the ViewModel

        recyclerview.adapter =
            StatisticsGraphAdapter()
    }

}
