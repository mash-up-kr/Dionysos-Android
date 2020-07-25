package com.mashup.dionysos.ui.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.mashup.dionysos.R
import com.mashup.dionysos.databinding.StatisticsGraphFragmentBinding
import com.mashup.dionysos.model.StatisticsGraph
import com.mashup.dionysos.ui.BaseFragment
import com.mashup.dionysos.ui.mypage.adapter.StatisticsGraphAdapter
import kotlinx.android.synthetic.main.statistics_graph_fragment.*

class StatisticsGraphFragment :
    BaseFragment<StatisticsGraphFragmentBinding>(R.layout.statistics_graph_fragment) {

    companion object {
        private const val CELL_LIST_KEY = "cellListKey"
        fun newInstance(cellList: ArrayList<StatisticsGraph>): Fragment {
            val fragment = StatisticsGraphFragment()

            val args = Bundle()
            args.putParcelableArrayList(CELL_LIST_KEY, cellList)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyclerview.adapter =
            StatisticsGraphAdapter()
        arguments?.getParcelableArrayList<StatisticsGraph>(CELL_LIST_KEY)?.run {
            (recyclerview.adapter as StatisticsGraphAdapter).submitList(this.toMutableList())
        }
    }
}
