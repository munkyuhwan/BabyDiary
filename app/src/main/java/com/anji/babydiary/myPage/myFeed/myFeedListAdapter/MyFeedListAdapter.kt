package com.anji.babydiary.myPage.myFeed.myFeedListAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anji.babydiary.database.mainFeed.MainFeed
import com.anji.babydiary.databinding.MainFeedListItemBinding
import com.anji.babydiary.databinding.MyFeedListItemBinding
import com.anji.babydiary.mainFeed.feedList.FeedClickListener
import com.anji.babydiary.mainFeed.feedList.listAdapter.MainFeedListAdapter
import com.anji.babydiary.myPage.myFeed.MyFeedClickListener
import com.bumptech.glide.Glide

class MyFeedListAdapter(val clickListener:MyFeedClickListener) :ListAdapter<MainFeed, MyFeedListAdapter.ViewHolder>(MyFeedDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class ViewHolder private constructor(val binding:MyFeedListItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item:MainFeed, onClickListener:MyFeedClickListener) {
            binding.feedData = item
            binding.clickListener = onClickListener
            Glide.with(binding.root.context).load(item.imgDir).into(binding.myFeedImages)
        }

        companion object {
            fun from(parent:ViewGroup):ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MyFeedListItemBinding.inflate(layoutInflater, parent, false)

                return MyFeedListAdapter.ViewHolder(binding)
            }
        }

    }


}

class MyFeedDiffUtilCallback:DiffUtil.ItemCallback<MainFeed>() {
    override fun areItemsTheSame(oldItem: MainFeed, newItem: MainFeed): Boolean {
        return oldItem.idx == newItem.idx
    }

    override fun areContentsTheSame(oldItem: MainFeed, newItem: MainFeed): Boolean {
        return oldItem == newItem
    }

}






