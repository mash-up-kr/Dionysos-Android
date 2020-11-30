package com.mashup.dionysos.ui.mypage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mashup.dionysos.databinding.TimeStampItemBinding
import com.mashup.dionysos.model.TimeStamp
import com.mashup.dionysos.ui.mypage.adapter.TimeStampAdapter.ViewHolder

class TimeStampAdapter :
    ListAdapter<TimeStamp, ViewHolder>(TimeStampDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: TimeStampItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TimeStamp) {
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TimeStampItemBinding.inflate(layoutInflater, parent, false)

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
class TimeStampDiffCallback : DiffUtil.ItemCallback<TimeStamp>() {
    override fun areItemsTheSame(oldItem: TimeStamp, newItem: TimeStamp): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TimeStamp, newItem: TimeStamp): Boolean {
        return oldItem == newItem
    }
}