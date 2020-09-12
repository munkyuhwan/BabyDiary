package com.anji.babydiary.myPage.chatting.chattingRoom

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anji.babydiary.database.chatting.Chatting
import com.anji.babydiary.database.chatting.ChattingAndUser
import com.anji.babydiary.databinding.ChattingRoomListItemBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions


class ChattingRoomListAdapter(val activity:Activity): ListAdapter<ChattingAndUser, ChattingRoomListAdapter.ViewHolder>(ChattingListDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)!!
        holder.bind(item, activity)
    }

    class ViewHolder private constructor(val binding: ChattingRoomListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind (item: ChattingAndUser, activity: Activity) {
            //binding.idx = item

            //binding.chatData = item
            if (item.chatting.isMyMessage) {
                binding.myText.text = item.chatting.msgText
                binding.YourTextLayout.visibility = View.GONE
                binding.myTextLayout.visibility = View.VISIBLE

                if (item.profile!!.imgTmp != "") {
                    Glide.with(binding.root.context)
                        .load(activity.resources.getIdentifier(item.profile!!.imgTmp, "drawable", activity.packageName))
                        .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(50)))
                        .into(binding.shapeableImageView)
                }else {
                    Glide.with(binding.root.context)
                        .load(item.profile!!.img)
                        .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(50)))
                        .into(binding.shapeableImageView)
                }

            }else {
                binding.reponderText.text = item.chatting.msgText
                binding.YourTextLayout.visibility = View.VISIBLE
                binding.myTextLayout.visibility = View.GONE
                if (item.profile!!.imgTmp != "") {
                    Glide.with(binding.root.context)
                        .load(activity.resources.getIdentifier(item.profile!!.imgTmp, "drawable", activity.packageName))
                        .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(50)))
                        .into(binding.shapeableImageView)
                }else {
                    Glide.with(binding.root.context)
                        .load(item.profile!!.img)
                        .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(50)))
                        .into(binding.shapeableImageView)
                }
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




class ChattingListDiffCallback: DiffUtil.ItemCallback<ChattingAndUser>() {
    override fun areItemsTheSame(oldItem: ChattingAndUser, newItem: ChattingAndUser): Boolean {
        return oldItem.chatting.idx == newItem.chatting.idx
    }

    override fun areContentsTheSame(oldItem: ChattingAndUser, newItem: ChattingAndUser): Boolean {
        return oldItem == newItem
    }

}

sealed class DataItem {

    abstract val id:Long

    data class ResultItem(val tip: ChattingAndUser):DataItem() {
        override val id = tip.chatting.idx
    }

    object Header:DataItem() {
        override val id = Long.MIN_VALUE
    }
}