package com.anji.babydiary.comment.commentListAdapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anji.babydiary.R
import com.anji.babydiary.database.comments.Comments
import com.anji.babydiary.databinding.CommentListItemBinding
import com.bumptech.glide.Glide
import com.google.android.material.shape.RoundedCornerTreatment
import com.google.android.material.shape.ShapePath


class CommentListAdapter:ListAdapter<Comments, CommentListAdapter.ViewHolder>(CommentListDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)!!
        val res = holder.itemView.context.resources
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: CommentListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind (item:Comments) {
            //binding.idx = item
            binding.userId.text = "sample"
            binding.commentText.text = item.commentText.toString()



            Glide.with(binding.root.context)
                .load(R.drawable.sample_1)
                .into(binding.userIcon)


            //binding.mainFeedText.text = item.text.toString()


            binding.executePendingBindings()

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


