package com.anji.babydiary.tips.tipsList.listAdapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anji.babydiary.common.Utils
import com.anji.babydiary.database.profile.ProfileDao
import com.anji.babydiary.database.profile.ProfileDatabase
import com.anji.babydiary.database.profile.Profiles
import com.anji.babydiary.database.tip.Tips
import com.anji.babydiary.databinding.TipListItemBinding
import com.anji.babydiary.tips.tipsList.TipClickListener
import com.anji.babydiary.tips.tipsList.TipCommentClicked
import com.anji.babydiary.tips.tipsList.TipLikeClicked
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.*

class TipListAdpater(val clickListener: TipClickListener, val tipLikeClicked: TipLikeClicked, val tipCommentClicked: TipCommentClicked, val activity:Activity, val lifecycleOwner: LifecycleOwner): ListAdapter<Tips, TipListAdpater.ViewHolder>(TipListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)!!
        holder.bind(item, clickListener,tipLikeClicked,  tipCommentClicked, activity, lifecycleOwner)
    }

    class ViewHolder private constructor(val binding: TipListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind (item: Tips, clickListener: TipClickListener, tipLikeClicked: TipLikeClicked,  tipCommentClicked: TipCommentClicked, activity:Activity, lifecycleOwner: LifecycleOwner) {
            //binding.idx = item


            binding.tips = item

            binding.likeClickListener = tipLikeClicked
            binding.tipLikeCnt.text = item.cnt.toString()
            binding.tipText.text = "${item.text.toString()} "

            Glide.with(binding.root.context).load(item.imgDir).into(binding.tipImg)
            Utils.setMyFeedListImg(binding.tipImg)



            val profileDatabase = ProfileDatabase.getInstance(activity.applicationContext).database
            val job = Job()
            val uiScope = CoroutineScope(Dispatchers.Main + job)
            val profileData = MutableLiveData<Profiles>()


            profileData.observe(lifecycleOwner, Observer {

                binding.tipUserId.text = it.name.toString()
                if (it.imgTmp != "") {
                    Glide.with(binding.root.context)
                        .load(activity.resources.getIdentifier(it.imgTmp, "drawable", activity.packageName))
                        .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(50)))
                        .into(binding.tipIcon)
                }else {
                    Glide.with(binding.root.context)
                        .load(it.img)
                        .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(50)))
                        .into(binding.tipIcon)
                }

            })


            uiScope.launch {
                setData(profileData, profileDatabase, item)
            }



            binding.executePendingBindings()
            binding.clickListener = clickListener
            binding.commentClickListener = tipCommentClicked
        }



        suspend fun setData(
            profileData: MutableLiveData<Profiles>,
            profileDatabase: ProfileDao,
            item: Tips
        ) {
            withContext(Dispatchers.IO) {
                profileData.postValue(profileDatabase.selectProfileData(item.user_idx))
            }
        }




        companion object {
            fun from(parent: ViewGroup):ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TipListItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }

        }

    }

}


class TipListDiffCallback: DiffUtil.ItemCallback<Tips>() {
    override fun areItemsTheSame(oldItem: Tips, newItem: Tips): Boolean {
        return oldItem.idx == newItem.idx
    }

    override fun areContentsTheSame(oldItem: Tips, newItem: Tips): Boolean {
        return oldItem == newItem
    }

}

sealed class DataItem {

    abstract val id:Long

    data class ResultItem(val tip:Tips):DataItem() {
        override val id = tip.idx
    }

    object Header:DataItem() {
        override val id = Long.MIN_VALUE
    }
}

