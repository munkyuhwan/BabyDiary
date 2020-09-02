package com.anji.babydiary.myPage.chatting.chattingRoom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anji.babydiary.database.chatting.Chatting
import com.anji.babydiary.databinding.ChattingRoomListItemBinding


class ChattingRoomListAdapter: ListAdapter<Chatting, ChattingRoomListAdapter.ViewHolder>(ChattingListDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)!!
        holder.bind(item)
    }

    class ViewHolder private constructor(val binding: ChattingRoomListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind (item: Chatting) {
            //binding.idx = item

            binding.chatData = item
            if (item.isMyMessage) {
                binding.myText.text = item.msgText
                binding.YourTextLayout.visibility = View.GONE
                binding.myTextLayout.visibility = View.VISIBLE
            }else {
                binding.reponderText.text = item.msgText
                binding.YourTextLayout.visibility = View.VISIBLE
                binding.myTextLayout.visibility = View.GONE
            }

        }

        companion object {
            fun from(parent: ViewGroup):ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ChattingRoomListItemBinding.inflate(layoutInflater, parent, false)

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

    data class ResultItem(val tip: Chatting):DataItem() {
        override val id = tip.idx
    }

    object Header:DataItem() {
        override val id = Long.MIN_VALUE
    }
}