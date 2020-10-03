package com.anji.babydiary.myPage.myFollower.listAdapter

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anji.babydiary.R
import com.anji.babydiary.database.follow.Follow
import com.anji.babydiary.database.mainFeed.MainFeed
import com.anji.babydiary.database.profile.ProfileDao
import com.anji.babydiary.database.profile.ProfileDatabase
import com.anji.babydiary.database.profile.Profiles
import com.anji.babydiary.databinding.FollowListItemBinding
import com.anji.babydiary.databinding.FollowerFragmentBinding
import com.anji.babydiary.databinding.MyFeedListItemBinding
import com.anji.babydiary.myPage.myFollower.FollowMemberClickListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.*

class FollowListAdapter(val activity:Activity, val type:String, val lifecycleOwner: LifecycleOwner, val FollowerClicked:FollowMemberClickListener) :ListAdapter<Follow, FollowListAdapter.FollowViewHolder>(FollowDiffUtilCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowViewHolder {
        return FollowViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FollowViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, activity, type, lifecycleOwner, FollowerClicked)
    }

    class FollowViewHolder private constructor(val binding:FollowListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item:Follow, activity: Activity, type:String, lifecycleOwner: LifecycleOwner, followerClicked:FollowMemberClickListener) {

            binding.folower = item
            binding.followerCLicked = followerClicked
            val profileDatabase = ProfileDatabase.getInstance(activity.applicationContext).database
            val job = Job()
            val uiScope = CoroutineScope(Dispatchers.Main + job)
            val profileData = MutableLiveData<Profiles>()
            uiScope.launch {
                if (type.equals("follower")) {
                    setData(profileData, profileDatabase, item.follower_idx)
                }
                else {
                    setData(profileData, profileDatabase, item.followee_idx)
                }
            }

            profileData.observe(lifecycleOwner, Observer {

                binding.userId.text = "${it.name}"
                binding.userIntro.text = "${it.introduce}"

                if (it.imgTmp != "") {
                    Glide.with(binding.root.context)
                        .load(activity.resources.getIdentifier(it.imgTmp, "drawable", activity.packageName))
                        .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(50)))
                        .into(binding.followImg)
                }else {
                    Glide.with(binding.root.context)
                        .load(it.img)
                        .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(50)))
                        .into(binding.followImg)
                }


                //Glide.with(binding.root.context).load(it.).into(binding.followImg)

            })

        }

        suspend fun setData(
            profileData: MutableLiveData<Profiles>,
            profileDatabase: ProfileDao,
            idx:Long
        ) {
            withContext(Dispatchers.IO) {
                profileData.postValue(profileDatabase.selectProfileData(idx))
            }
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

