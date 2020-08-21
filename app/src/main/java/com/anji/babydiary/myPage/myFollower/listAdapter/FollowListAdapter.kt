package com.anji.babydiary.myPage.myFollower.listAdapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anji.babydiary.R
import com.anji.babydiary.database.follow.Follow
import com.anji.babydiary.databinding.FollowListItemBinding
import com.anji.babydiary.databinding.FollowerFragmentBinding
import com.anji.babydiary.databinding.MyFeedListItemBinding
import com.bumptech.glide.Glide

class FollowListAdapter :ListAdapter<Follow, FollowListAdapter.FollowViewHolder>(FollowDiffUtilCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowViewHolder {
        return FollowViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FollowViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class FollowViewHolder private constructor(val binding:FollowListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item:Follow) {

            binding.userId.text = item.followee
            binding.userIntro.text = "소개글글글"

            Glide.with(binding.root.context).load(R.drawable.ic_launcher_background).into(binding.followImg)

        }

        companion object {
            fun from(parent: ViewGroup): FollowViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FollowListItemBinding.inflate(layoutInflater, parent, false)

                return FollowListAdapter.FollowViewHolder(binding)
            }
        }


    }

}

class FollowDiffUtilCallback:DiffUtil.ItemCallback<Follow>() {
    override fun areItemsTheSame(oldItem: Follow, newItem: Follow): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Follow, newItem: Follow): Boolean {
        return newItem.idx == oldItem.idx
    }

}

