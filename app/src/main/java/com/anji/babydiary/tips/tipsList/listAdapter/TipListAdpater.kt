package com.anji.babydiary.tips.tipsList.listAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anji.babydiary.database.shopping.Tip
import com.anji.babydiary.databinding.TipListItemBinding
import com.anji.babydiary.tips.tipsList.TipClickListener
import com.bumptech.glide.Glide

class TipListAdpater(val clickListener: TipClickListener): ListAdapter<Tip, TipListAdpater.ViewHolder>(TipListDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)!!
        val res = holder.itemView.context.resources
        holder.bind(item, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: TipListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind (item: Tip, clickListener: TipClickListener) {
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


class TipListDiffCallback: DiffUtil.ItemCallback<Tip>() {
    override fun areItemsTheSame(oldItem: Tip, newItem: Tip): Boolean {
        return oldItem.idx == newItem.idx
    }

    override fun areContentsTheSame(oldItem: Tip, newItem: Tip): Boolean {
        return oldItem == newItem
    }

}

sealed class DataItem {

    abstract val id:Long

    data class ResultItem(val tip:Tip):DataItem() {
        override val id = tip.idx
    }

    object Header:DataItem() {
        override val id = Long.MIN_VALUE
    }
}

