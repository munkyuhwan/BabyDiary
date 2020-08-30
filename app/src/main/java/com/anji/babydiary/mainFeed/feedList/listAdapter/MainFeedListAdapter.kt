package com.anji.babydiary.mainFeed.feedList.listAdapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anji.babydiary.database.mainFeed.MainFeed
import com.anji.babydiary.database.profile.ProfileDatabase
import com.anji.babydiary.databinding.MainFeedListItemBinding
import com.anji.babydiary.mainFeed.MainFeedViewModel
import com.anji.babydiary.mainFeed.feedList.FeedClickListener
import com.anji.babydiary.mainFeed.feedList.FeedCommentClickListener
import com.anji.babydiary.mainFeed.feedList.FeedListViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job


class MainFeedListAdapter(val clickListener: FeedClickListener, val commentClickListener:FeedCommentClickListener, val feedListViewModel: FeedListViewModel):ListAdapter<MainFeed, MainFeedListAdapter.ViewHolder>(ResultListDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)!!
        val res = holder.itemView.context.resources
        holder.bind(item, feedListViewModel, clickListener, commentClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: MainFeedListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind (item:MainFeed,feedListViewModel: FeedListViewModel, clickListener: FeedClickListener, commentClickListener:FeedCommentClickListener) {
            //binding.idx = item

            binding.mainFeed = item
            binding.viewModel = feedListViewModel
            binding.likeCnt.text = item.likeCnt.toString()
            binding.mainFeedText.text = item.title.toString()

            Glide.with(binding.root.context)
                .load(item.imgDir)
                .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(50)))
                .into(binding.feedImg)


            //binding.mainFeedText.text = item.text.toString()
            binding.userId.text = item.userId.toString()


            binding.executePendingBindings()
            binding.clickListener = clickListener
            binding.commentClickListener = commentClickListener

        }


        companion object {
            fun from(parent: ViewGroup):ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =MainFeedListItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }

        }

    }

}


class ResultListDiffCallback: DiffUtil.ItemCallback<MainFeed>() {
    override fun areItemsTheSame(oldItem: MainFeed, newItem: MainFeed): Boolean {
        return oldItem.idx == newItem.idx
    }

    override fun areContentsTheSame(oldItem: MainFeed, newItem: MainFeed): Boolean {
        return oldItem == newItem
    }

}

sealed class DataItem {

    abstract val id:Long

    data class ResultItem(val gameResult:MainFeed):DataItem() {
        override val id = gameResult.idx
    }

    object Header:DataItem() {
        override val id = Long.MIN_VALUE
    }
}



