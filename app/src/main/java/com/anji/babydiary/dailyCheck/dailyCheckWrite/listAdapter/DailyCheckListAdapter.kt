package com.anji.babydiary.dailyCheck.dailyCheckWrite.listAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anji.babydiary.dailyCheck.DailyCheckListObj
import com.anji.babydiary.database.dailyCheck.DailyCheck
import com.anji.babydiary.database.event.Event
import com.anji.babydiary.databinding.DailyCheckListAdapterBinding
import com.anji.babydiary.databinding.EventListItemBinding
import com.anji.babydiary.event.eventList.listAdapter.EventListAdapter

class DailyCheckListAdapter:ListAdapter<DailyCheck, DailyCheckListAdapter.DailyCheckViewHolder>(DailyCHeckListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyCheckViewHolder {
        return DailyCheckListAdapter.DailyCheckViewHolder.from(parent)

    }

    override fun onBindViewHolder(holder: DailyCheckViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class DailyCheckViewHolder private constructor(val binding:DailyCheckListAdapterBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(item:DailyCheck){
            binding.dailyCheck = item
            binding.checkCategory.text = DailyCheckListObj.itemName[item.category]
            binding.checkText.text = item.valueOne
            binding.checkTime.text = "${item.hour}:${item.minute}"
            binding.checkIcon.setBackgroundResource(DailyCheckListObj.itemBackground[item.category])
            binding.checkIcon.setImageResource(DailyCheckListObj.itemSrc[item.category])

        }

        companion object {
            fun from(parent: ViewGroup):DailyCheckViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DailyCheckListAdapterBinding.inflate(layoutInflater, parent, false)
                return DailyCheckViewHolder(binding)
            }
        }


    }
}

class DailyCHeckListDiffCallback: DiffUtil.ItemCallback<DailyCheck>() {
    override fun areItemsTheSame(oldItem: DailyCheck, newItem: DailyCheck): Boolean {
        return oldItem.idx == newItem.idx
    }

    override fun areContentsTheSame(oldItem: DailyCheck, newItem: DailyCheck): Boolean {
        return oldItem == newItem
    }

}




