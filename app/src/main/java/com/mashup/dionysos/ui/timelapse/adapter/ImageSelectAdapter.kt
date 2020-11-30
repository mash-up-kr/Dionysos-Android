package com.mashup.dionysos.ui.timelapse.adapter

import android.app.Activity
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mashup.dionysos.R
import com.mashup.dionysos.databinding.ItemImageSelectBinding
import com.mashup.dionysos.model.ImageSelect


class ImageSelectAdapter(private val item: ArrayList<ImageSelect>) :
    RecyclerView.Adapter<ImageSelectAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemImageSelectBinding>(
            layoutInflater, R.layout.item_image_select, parent, false
        )
        return Holder(binding)
    }

    override fun getItemCount() = item.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val displayMetrics = DisplayMetrics()
        (holder.itemView.context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        val deviceWidth = displayMetrics.widthPixels
        val width: Int = deviceWidth / item.size + deviceWidth % item.size
        holder.itemView.layoutParams.width = width
        holder.itemView.requestLayout()
        holder.bind(item, position)

        holder.itemView.setOnClickListener {
            item.forEach{
                it.visible = false
            }
            item[position].visible = true
            this.notifyDataSetChanged()
            mListener?.let {
                it.onItemClick(position) ;
            }
        }
    }

    class Holder(private val binding: ItemImageSelectBinding) :
        RecyclerView.ViewHolder(binding.imageSelect) {
        fun bind(item: ArrayList<ImageSelect>, position: Int) {
            binding.item = item[position]
        }
    }

    private var mListener: OnItemClickListener? = null

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    fun setOnItemClickListener(listener: OnItemClickListener?) {
        mListener = listener
    }
}
interface OnItemClickListener {
    fun onItemClick(position: Int)
}

