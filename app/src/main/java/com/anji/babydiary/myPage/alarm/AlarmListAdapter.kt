package com.anji.babydiary.myPage.alarm

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anji.babydiary.database.alarm.Alarm
import com.anji.babydiary.database.chatting.Chatting
import com.anji.babydiary.databinding.AlarmListItemBinding
import com.anji.babydiary.databinding.ChattingListItemBinding
import com.anji.babydiary.myPage.chatting.ChattingList.chattingListAdapter.ChattingListAdapter
import com.anji.babydiary.myPage.chatting.chattingList.OnChattingListClick

class AlarmListAdapter : ListAdapter<Alarm, AlarmListAdapter.ViewHolder>(AlarmListDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)!!
        holder.bind(item)
    }

    class ViewHolder private constructor(val binding: AlarmListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind (item: Alarm) {
            //binding.idx = item

            binding.alarmTitle.text = item.title.toString()
            binding.alarmText.text = item.msg.toString()

            /*
            binding.chattingList = item
            binding.onClick = clickListener
            binding.userName.text = item.responderName
            binding.lastText.text = item.msgText
            */


        }

        companion object {
            fun from(parent: ViewGroup):ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AlarmListItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }

        }

    }


}




class AlarmListDiffCallback: DiffUtil.ItemCallback<Alarm>() {
    override fun areItemsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
        return oldItem.idx == newItem.idx
    }

    override fun areContentsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
        return oldItem == newItem
    }

}

sealed class DataItem {

    abstract val id:Long

    data class ResultItem(val tip: Alarm):DataItem() {
        override val id = tip.idx
    }

    object Header:DataItem() {
        override val id = Long.MIN_VALUE
    }
}