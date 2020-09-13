package com.anji.babydiary.tips.tipsComment

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anji.babydiary.R
import com.anji.babydiary.common.Utils
import com.anji.babydiary.database.tip.tipsComment.TipsCommentWithUser
import com.anji.babydiary.databinding.CommentListItemBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class TipsCommentListAdapter(val activity:Activity): ListAdapter<TipsCommentWithUser, TipsCommentListAdapter.ViewHolder>(TipsCommentListDiffCallback())  {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)!!
        val res = holder.itemView.context.resources
        holder.bind(item, activity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: CommentListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind (item:TipsCommentWithUser, activity:Activity) {
            //binding.idx = item
            binding.userId.text = "${item.prof!!.name}"
            binding.commentText.text = item.tips!!.commentText.toString()


            if (item.prof!!.imgTmp != "") {
                Glide.with(binding.root.context)
                    .load(  activity.resources.getIdentifier(item.prof!!.imgTmp, "drawable", activity.packageName))
                    .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(50)))
                    .into(binding.userIcon)



            }else {
                Glide.with(binding.root.context)
                    .load(item.prof!!.img)
                    .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(50)))
                    .into(binding.userIcon)
            }


            Glide.with(binding.root.context)
                .load(R.drawable.sample_1)
                .into(binding.userIcon)


            //binding.mainFeedText.text = item.text.toString()


            binding.executePendingBindings()

        }


        companion object {
            fun from(parent: ViewGroup):ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CommentListItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }

        }

    }

}


class TipsCommentListDiffCallback: DiffUtil.ItemCallback<TipsCommentWithUser>() {
    override fun areItemsTheSame(oldItem: TipsCommentWithUser, newItem: TipsCommentWithUser): Boolean {
        return oldItem.tips!!.idx == newItem.tips!!.idx
    }

    override fun areContentsTheSame(oldItem: TipsCommentWithUser, newItem: TipsCommentWithUser): Boolean {
        return oldItem == newItem
    }

}

sealed class DataItem {

    abstract val id:Long

    data class ResultItem(val gameResult:TipsCommentWithUser):DataItem() {
        override val id = gameResult.tips!!.idx
    }

    object Header:DataItem() {
        override val id = Long.MIN_VALUE
    }
}



