package com.anji.babydiary.tips.tipsList.listAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anji.babydiary.database.shopping.Tips
import com.anji.babydiary.databinding.TipListItemBinding
import com.anji.babydiary.tips.tipsList.TipClickListener
import com.bumptech.glide.Glide

class TipListAdpater(val clickListener: TipClickListener): ListAdapter<Tips, TipListAdpater.ViewHolder>(TipListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)!!
        holder.bind(item, clickListener)
    }

    class ViewHolder private constructor(val binding: TipListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind (item: Tips, clickListener: TipClickListener) {
            //binding.idx = item

            binding.tipLikeCnt.text = item.cnt.toString()
            binding.tipText.text = "${item.text.toString()} "
            binding.tipUserId.text = item.user.toString()

            Glide.with(binding.root.context).load(item.imgDir).into(binding.tipImg)

            binding.executePendingBindings()
            binding.clickListener = clickListener

        }


        companion object {
            fun from(parent: ViewGroup):ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TipListItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }

        }

    }

}


class TipListDiffCallback: DiffUtil.ItemCallback<Tips>() {
    override fun areItemsTheSame(oldItem: Tips, newItem: Tips): Boolean {
        return oldItem.idx == newItem.idx
    }

    override fun areContentsTheSame(oldItem: Tips, newItem: Tips): Boolean {
        return oldItem == newItem
    }

}

sealed class DataItem {

    abstract val id:Long

    data class ResultItem(val tip:Tips):DataItem() {
        override val id = tip.idx
    }

    object Header:DataItem() {
        override val id = Long.MIN_VALUE
    }
}

