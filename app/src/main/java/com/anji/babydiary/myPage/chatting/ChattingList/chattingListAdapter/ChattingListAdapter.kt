package com.anji.babydiary.myPage.chatting.ChattingList.chattingListAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anji.babydiary.database.chatting.Chatting
import com.anji.babydiary.database.shopping.Tips
import com.anji.babydiary.databinding.ChattingListItemBinding
import com.anji.babydiary.databinding.TipListItemBinding
import com.anji.babydiary.myPage.chatting.ChattingList.OnChattingListClick
import com.anji.babydiary.tips.tipsList.TipClickListener
import com.bumptech.glide.Glide

class ChattingListAdapter(val clickListener:OnChattingListClick): ListAdapter<Chatting, ChattingListAdapter.ViewHolder>(ChattingListDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)!!
        holder.bind(item, clickListener)
    }

    class ViewHolder private constructor(val binding:ChattingListItemBinding ) : RecyclerView.ViewHolder(binding.root) {

        fun bind (item: Chatting, clickListener: OnChattingListClick) {
            //binding.idx = item

        }


        companion object {
            fun from(parent: ViewGroup):ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ChattingListItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }

        }

    }


}




class ChattingListDiffCallback: DiffUtil.ItemCallback<Chatting>() {
    override fun areItemsTheSame(oldItem: Chatting, newItem: Chatting): Boolean {
        return oldItem.idx == newItem.idx
    }

    override fun areContentsTheSame(oldItem: Chatting, newItem: Chatting): Boolean {
        return oldItem == newItem
    }

}

sealed class DataItem {

    abstract val id:Long

    data class ResultItem(val tip:Chatting):DataItem() {
        override val id = tip.idx
    }

    object Header:DataItem() {
        override val id = Long.MIN_VALUE
    }
}