package com.mashup.dionysos.ui.mypage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mashup.dionysos.databinding.CellItemBinding
import com.mashup.dionysos.model.StatisticsGraph
import com.mashup.dionysos.ui.mypage.adapter.StatisticsGraphAdapter.ViewHolder

class StatisticsGraphAdapter :
    ListAdapter<StatisticsGraph, ViewHolder>(StatisticsDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: CellItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: StatisticsGraph) {
            binding.studyTime = item.studyTime
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CellItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minimum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class StatisticsDiffCallback : DiffUtil.ItemCallback<StatisticsGraph>() {
    override fun areItemsTheSame(oldItem: StatisticsGraph, newItem: StatisticsGraph): Boolean {
        return oldItem.day == newItem.day
    }

    override fun areContentsTheSame(oldItem: StatisticsGraph, newItem: StatisticsGraph): Boolean {
        return oldItem == newItem
    }
}