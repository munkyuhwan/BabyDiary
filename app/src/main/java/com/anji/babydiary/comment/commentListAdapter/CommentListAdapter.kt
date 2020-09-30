package com.anji.babydiary.comment.commentListAdapter


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
import com.anji.babydiary.comment.CommentIdClick
import com.anji.babydiary.common.Utils
import com.anji.babydiary.database.comments.Comments
import com.anji.babydiary.database.profile.ProfileDao
import com.anji.babydiary.database.profile.ProfileDatabase
import com.anji.babydiary.database.profile.Profiles
import com.anji.babydiary.databinding.CommentListItemBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.*


class CommentListAdapter(val activity: Activity, val lifecycleOwner: LifecycleOwner, val onIdClick: CommentIdClick):ListAdapter<Comments, CommentListAdapter.ViewHolder>(CommentListDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)!!
        val res = holder.itemView.context.resources
        holder.bind(item,activity, lifecycleOwner, onIdClick)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: CommentListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind (item:Comments, activity:Activity, lifecycleOwner: LifecycleOwner, onIdClick: CommentIdClick) {
            //binding.idx = item
            Log.e("profile","====================================================")
            Log.e("profile","${item}")
            Log.e("profile","====================================================")
            binding.comment = item
            binding.idClick = onIdClick
            binding.commentText.text = item.commentText.toString()

            val profileDatabase = ProfileDatabase.getInstance(activity.applicationContext).database
            val job = Job()
            val uiScope = CoroutineScope(Dispatchers.Main + job)
            val profileData = MutableLiveData<Profiles>()

            profileData.observe(lifecycleOwner, Observer {
                Log.e("profileData","====================================================")
                Log.e("profileData","${item}")
                Log.e("profileData","====================================================")

                binding.userId.text = "${it.name}"

                if(it.imgTmp != "") {
                    Glide.with(binding.root.context)
                        .load(activity.resources.getIdentifier(it.imgTmp, "drawable", activity.packageName))
                        .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(50)))
                        .into(binding.userIcon)
                    binding.executePendingBindings()
                }else {
                    Glide.with(binding.root.context)
                        .load(it.img)
                        .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(50)))
                        .into(binding.userIcon)
                    binding.executePendingBindings()
                }
            })

            uiScope.launch {
                setData(profileData, profileDatabase, item)
            }

            binding.commentDate.text = "${Utils.getDate(item.date,"yyyy.mm.dd HH:mm" )}"

            binding.executePendingBindings()

        }

        suspend fun setData(
            profileData: MutableLiveData<Profiles>,
            profileDatabase: ProfileDao,
            item: Comments
        ) {
            withContext(Dispatchers.IO) {
                profileData.postValue(profileDatabase.selectProfileData(item.userIdx))
            }
        }


        companion object {
            fun from(parent: ViewGroup):ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =CommentListItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }

        }

    }

}

class CommentListDiffCallback: DiffUtil.ItemCallback<Comments>() {
    override fun areItemsTheSame(oldItem: Comments, newItem: Comments): Boolean {
        return oldItem.idx == newItem.idx
    }

    override fun areContentsTheSame(oldItem: Comments, newItem: Comments): Boolean {
        return oldItem == newItem
    }

}

sealed class DataItem {

    abstract val id:Long

    data class ResultItem(val gameResult:Comments):DataItem() {
        override val id = gameResult.idx
    }

    object Header:DataItem() {
        override val id = Long.MIN_VALUE
    }
}


