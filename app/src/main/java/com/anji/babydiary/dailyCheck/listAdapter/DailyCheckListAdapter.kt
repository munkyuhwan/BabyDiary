package com.anji.babydiary.dailyCheck.listAdapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anji.babydiary.common.Utils
import com.anji.babydiary.dailyCheck.DailyCheckListObj
import com.anji.babydiary.database.dailyCheck.DailyCheck
import com.anji.babydiary.databinding.DailyCheckListAdapterBinding

class DailyCheckListAdapter(val isDetail:Boolean):ListAdapter<DailyCheck, DailyCheckListAdapter.DailyCheckViewHolder>(DailyCHeckListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyCheckViewHolder {
        return DailyCheckListAdapter.DailyCheckViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: DailyCheckViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, isDetail)
    }

    class DailyCheckViewHolder private constructor(val binding:DailyCheckListAdapterBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(item:DailyCheck, isDetail: Boolean){
            binding.dailyCheck = item
            binding.checkCategory.text = DailyCheckListObj.itemName[item.category]

            if (item.valueTwo!="") {
                binding.checkText.text = "${Utils.convertToTime(item.valueOne)} ${Utils.convertToTime(item.valueTwo)}"
            }else {
                binding.checkText.text = "${item.valueOne}"
            }
            if (!isDetail) {
                binding.editRecordBtn.visibility = View.GONE
                binding.checkText.setTextColor( Color.parseColor("#ffffff") )
                binding.checkTime.setTextColor( Color.parseColor("#ffffff") )
                binding.checkCategory.setTextColor( Color.parseColor("#ffffff") )
            }

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




