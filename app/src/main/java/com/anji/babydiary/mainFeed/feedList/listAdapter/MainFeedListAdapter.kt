package com.anji.babydiary.mainFeed.feedList.listAdapter

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
import com.anji.babydiary.common.Utils
import com.anji.babydiary.database.comments.Comments
import com.anji.babydiary.database.mainFeed.MainFeed
import com.anji.babydiary.database.profile.ProfileDao
import com.anji.babydiary.database.profile.ProfileDatabase
import com.anji.babydiary.database.profile.Profiles
import com.anji.babydiary.databinding.MainFeedListItemBinding
import com.anji.babydiary.mainFeed.feedList.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.*
import java.io.File


class MainFeedListAdapter(val clickListener: FeedClickListener, val commentClickListener:FeedCommentClickListener, val memberClickListener:MemberClickListener, val bookMarkClickListener: BookMarkClickListener, val feedListViewModel: FeedListViewModel, val activity:Activity, val lifecycleOwner: LifecycleOwner):ListAdapter<MainFeed, MainFeedListAdapter.ViewHolder>(ResultListDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)!!
        val res = holder.itemView.context.resources
        holder.bind(activity, item, feedListViewModel, clickListener, commentClickListener, memberClickListener, bookMarkClickListener, lifecycleOwner)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: MainFeedListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind (activity: Activity, item: MainFeed, feedListViewModel: FeedListViewModel, clickListener: FeedClickListener, commentClickListener:FeedCommentClickListener, memberClickListener: MemberClickListener, bookMarkClickListener: BookMarkClickListener, lifecycleOwner: LifecycleOwner) {
            //binding.idx = item

            binding.mainFeed = item
            binding.memberClickListener = memberClickListener
            binding.viewModel = feedListViewModel
            binding.likeCnt.text = item.likeCnt.toString()
            binding.mainFeedText.text = item.title.toString()
            
            val currentTimeMille = System.currentTimeMillis()


            val hour = (currentTimeMille - item.timeMilli)/3600000

            //binding.textTime.text = "${hour.toString()} 시간 전"


            if (item.imgTmpDir != "") {
                Glide.with(binding.root.context)
                    .load(  activity.resources.getIdentifier(item.imgTmpDir, "drawable", activity.packageName))
                    //.apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(50)))
                    .into(binding.feedImg)
                Utils.setFeedListImg(binding.feedImg)
            }else {
                Glide.with(binding.root.context)
                    .load(item.imgDir)
                    //.apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(50)))
                    .into(binding.feedImg)
                Utils.setFeedListImg(binding.feedImg)
            }


            val profileDatabase = ProfileDatabase.getInstance(activity.applicationContext).database
            val job = Job()
            val uiScope = CoroutineScope(Dispatchers.Main + job)
            val profileData = MutableLiveData<Profiles>()

            profileData.observe(lifecycleOwner, Observer {

                binding.userId.text = it.name.toString()
                if (it.imgTmp != "") {
                    Glide.with(binding.root.context)
                        .load(activity.resources.getIdentifier(it.imgTmp, "drawable", activity.packageName))
                        .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(50)))
                        .into(binding.userIcon)
                }else {
                    Glide.with(binding.root.context)
                        .load(it.img)
                        .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(50)))
                        .into(binding.userIcon)
                }


            })

            uiScope.launch {
                setData(profileData, profileDatabase, item)
            }

            binding.executePendingBindings()
            binding.clickListener = clickListener
            binding.commentClickListener = commentClickListener
            binding.bookmarkClickListener = bookMarkClickListener




        }


        suspend fun setData(
            profileData: MutableLiveData<Profiles>,
            profileDatabase: ProfileDao,
            item: MainFeed
        ) {
            withContext(Dispatchers.IO) {
                profileData.postValue(profileDatabase.selectProfileData(item.userIdx))
            }
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



