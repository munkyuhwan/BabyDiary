package com.anji.babydiary.myPage.chatting.chattingRoom

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anji.babydiary.database.chatting.Chatting
import com.anji.babydiary.database.profile.ProfileDao
import com.anji.babydiary.database.profile.ProfileDatabase
import com.anji.babydiary.database.profile.Profiles
import com.anji.babydiary.databinding.ChattingRoomListItemBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.*


class ChattingRoomListAdapter(val activity:Activity, val lifecycleOwner: LifecycleOwner): ListAdapter<Chatting, ChattingRoomListAdapter.ViewHolder>(ChattingListDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)!!
        holder.bind(item, activity, lifecycleOwner)
    }

    class ViewHolder private constructor(val binding: ChattingRoomListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind (item: Chatting, activity: Activity, lifecycleOwner: LifecycleOwner) {
            //binding.idx = item


            val profileDatabase = ProfileDatabase.getInstance(activity.applicationContext).database
            val job = Job()
            val uiScope = CoroutineScope(Dispatchers.Main + job)
            val profileData = MutableLiveData<Profiles>()

            //binding.chatData = item
            if (item.isMyMessage) {
                binding.myText.text = item.msgText
                binding.YourTextLayout.visibility = View.GONE
                binding.myTextLayout.visibility = View.VISIBLE


                profileData.observe(lifecycleOwner, Observer {

                    if (it.imgTmp != "") {
                        Glide.with(binding.root.context)
                            .load(
                                activity.resources.getIdentifier(
                                    it.imgTmp,
                                    "drawable",
                                    activity.packageName
                                )
                            )
                            .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(50)))
                            .into(binding.shapeableImageView)
                    } else {
                        Glide.with(binding.root.context)
                            .load(it.img)
                            .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(50)))
                            .into(binding.shapeableImageView)
                    }
                })

            }else {
                binding.reponderText.text = item.msgText
                binding.YourTextLayout.visibility = View.VISIBLE
                binding.myTextLayout.visibility = View.GONE

                profileData.observe(lifecycleOwner, Observer {

                    if (it.imgTmp != "") {
                        Glide.with(binding.root.context)
                            .load(
                                activity.resources.getIdentifier(
                                    it.imgTmp,
                                    "drawable",
                                    activity.packageName
                                )
                            )
                            .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(50)))
                            .into(binding.shapeableImageView)
                    } else {
                        Glide.with(binding.root.context)
                            .load(it.img)
                            .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(50)))
                            .into(binding.shapeableImageView)
                    }
                })

                uiScope.launch {
                    setData(profileData, profileDatabase, item)
                }
            }

        }


        suspend fun setData(
            profileData: MutableLiveData<Profiles>,
            profileDatabase: ProfileDao,
            item: Chatting
        ) {
            withContext(Dispatchers.IO) {
                profileData.postValue(profileDatabase.selectProfileData(item.userIdxOne))
            }
        }


        companion object {
            fun from(parent: ViewGroup):ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ChattingRoomListItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }

        }

    }


}




class ChattingListDiffCallback: DiffUtil.ItemCallback<Chatting>() {
    override fun areItemsTheSame(oldItem: Chatting, newItem: Chatting): Boolean {
        return oldItem.idx == newItem.idx
    }

    override fun areContentsTheSame(oldItem: Chatting, newItem: Chatting): Boolean {
        return oldItem == newItem
    }

}

sealed class DataItem {

    abstract val id:Long

    data class ResultItem(val tip: Chatting):DataItem() {
        override val id = tip.idx
    }

    object Header:DataItem() {
        override val id = Long.MIN_VALUE
    }
}