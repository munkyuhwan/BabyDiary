package com.anji.babydiary.event.eventList.listAdapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anji.babydiary.common.Utils
import com.anji.babydiary.database.event.Event
import com.anji.babydiary.databinding.EventListItemBinding
import com.anji.babydiary.event.eventList.EventListClickListener
import com.bumptech.glide.Glide

class EventListAdapter (val eventListClickListener: EventListClickListener, val activity:Activity):ListAdapter<Event, EventListAdapter.ViewHolder>(EventListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, eventListClickListener, activity)
    }


    class ViewHolder private constructor(val binding:EventListItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(item:Event, onClickListener: EventListClickListener, activity:Activity) {
            binding.event = item
            binding.eventTitle.text = item.title


            Glide.with(binding.root.context)
                .load(  activity.resources.getIdentifier(item.imgDir, "drawable", activity.packageName))
                //.apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(50)))
                .into(binding.eventListImg)
            Utils.setMyFeedListImg(binding.eventListImg)


            binding.clickListener = onClickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent:ViewGroup):ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = EventListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}


class EventListDiffCallback: DiffUtil.ItemCallback<Event>() {
    override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
        return oldItem.idx == newItem.idx
    }

    override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
        return oldItem == newItem
    }

}
