package com.anji.babydiary.myPage.myFeed.myFeedListAdapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anji.babydiary.common.Utils
import com.anji.babydiary.database.mainFeed.MainFeed
import com.anji.babydiary.databinding.MainFeedListItemBinding
import com.anji.babydiary.databinding.MyFeedListItemBinding
import com.anji.babydiary.mainFeed.feedList.FeedClickListener
import com.anji.babydiary.mainFeed.feedList.listAdapter.MainFeedListAdapter
import com.anji.babydiary.myPage.myFeed.MyFeedClickListener
import com.bumptech.glide.Glide

class MyFeedListAdapter(val clickListener:MyFeedClickListener, val activity:Activity) :ListAdapter<MainFeed, MyFeedListAdapter.MyFeedViewHolder>(MyFeedDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyFeedViewHolder {
        return MyFeedViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyFeedViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener, activity)
    }

    class MyFeedViewHolder private constructor(val binding:MyFeedListItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item:MainFeed, onClickListener:MyFeedClickListener, activity: Activity) {

            binding.clickListener = onClickListener
            binding.feedData = item

            if (item.imgTmpDir != "") {
                Glide.with(binding.root.context)
                    .load(activity.resources.getIdentifier(item.imgTmpDir, "drawable", activity.packageName))
                    .into(binding.myFeedImages)
                Utils.setMyFeedListImg(binding.myFeedImages)
            }else {
                Glide.with(binding.root.context).load(item.imgDir).into(binding.myFeedImages)
                Utils.setMyFeedListImg(binding.myFeedImages)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent:ViewGroup):MyFeedViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MyFeedListItemBinding.inflate(layoutInflater, parent, false)

                return MyFeedViewHolder(binding)
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






