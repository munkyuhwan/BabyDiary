package com.anji.babydiary.mainFeed.feedList.listAdapter

import android.R
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anji.babydiary.common.Utils
import com.anji.babydiary.database.mainFeed.FeedWithUser
import com.anji.babydiary.databinding.MainFeedListItemBinding
import com.anji.babydiary.mainFeed.feedList.FeedClickListener
import com.anji.babydiary.mainFeed.feedList.FeedCommentClickListener
import com.anji.babydiary.mainFeed.feedList.FeedListViewModel
import com.anji.babydiary.mainFeed.feedList.MemberClickListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.shape.CornerFamily


class MainFeedListAdapter(val clickListener: FeedClickListener, val commentClickListener:FeedCommentClickListener, val memberClickListener:MemberClickListener, val feedListViewModel: FeedListViewModel, val activity:Activity):ListAdapter<FeedWithUser, MainFeedListAdapter.ViewHolder>(ResultListDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)!!
        val res = holder.itemView.context.resources
        holder.bind(activity, item, feedListViewModel, clickListener, commentClickListener, memberClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: MainFeedListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind (activity: Activity, item: FeedWithUser, feedListViewModel: FeedListViewModel, clickListener: FeedClickListener, commentClickListener:FeedCommentClickListener, memberClickListener: MemberClickListener) {
            //binding.idx = item

            binding.mainFeedWithUser = item
            binding.memberClickListener = memberClickListener
            binding.viewModel = feedListViewModel
            binding.likeCnt.text = item.feed.likeCnt.toString()
            binding.mainFeedText.text = item.feed.title.toString()


            //resources.getIdentifier(it.imgTmp, "drawable", requireActivity().packageName)


            if (item.feed.imgTmpDir != "") {
                Glide.with(binding.root.context)
                    .load(  activity.resources.getIdentifier(item.feed.imgTmpDir, "drawable", activity.packageName))
                    //.apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(50)))
                    .into(binding.feedImg)

                Utils.setFeedListImg(binding.feedImg)


            }else {
                Glide.with(binding.root.context)
                    .load(item.feed.imgDir)
                    //.apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(50)))
                    .into(binding.feedImg)
                Utils.setFeedListImg(binding.feedImg)
            }

            binding.userId.text = item.userProfile.name.toString()

            if (item.userProfile.imgTmp != "") {
                Glide.with(binding.root.context)
                    .load(activity.resources.getIdentifier(item.userProfile.imgTmp, "drawable", activity.packageName))
                    .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(50)))
                    .into(binding.userIcon)
            }else {
                Glide.with(binding.root.context)
                    .load(item.userProfile.img)
                    .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(50)))
                    .into(binding.userIcon)
            }

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


class ResultListDiffCallback: DiffUtil.ItemCallback<FeedWithUser>() {
    override fun areItemsTheSame(oldItem: FeedWithUser, newItem: FeedWithUser): Boolean {
        return oldItem.feed.idx == newItem.feed.idx
    }

    override fun areContentsTheSame(oldItem: FeedWithUser, newItem: FeedWithUser): Boolean {
        return oldItem == newItem
    }

}

sealed class DataItem {

    abstract val id:Long

    data class ResultItem(val gameResult:FeedWithUser):DataItem() {
        override val id = gameResult.feed.idx
    }

    object Header:DataItem() {
        override val id = Long.MIN_VALUE
    }
}



