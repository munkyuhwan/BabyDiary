package com.anji.babydiary.tips.tipsList.listAdapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anji.babydiary.database.tip.TipWithUser
import com.anji.babydiary.databinding.TipListItemBinding
import com.anji.babydiary.tips.tipsList.TipClickListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class TipListAdpater(val clickListener: TipClickListener, val activity:Activity): ListAdapter<TipWithUser, TipListAdpater.ViewHolder>(TipListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)!!
        holder.bind(item, clickListener, activity)
    }

    class ViewHolder private constructor(val binding: TipListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind (item: TipWithUser, clickListener: TipClickListener, activity:Activity) {
            //binding.idx = item

            binding.tipLikeCnt.text = item.tips!!.cnt.toString()
            binding.tipText.text = "${item.tips!!.text.toString()} "



            binding.tipUserId.text = item.prof.name.toString()

            Glide.with(binding.root.context).load(item.tips!!.imgDir).into(binding.tipImg)
            if (item.prof.imgTmp != "") {
                Glide.with(binding.root.context)
                    .load(activity.resources.getIdentifier(item.prof.imgTmp, "drawable", activity.packageName))
                    .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(50)))
                    .into(binding.tipIcon)
            }else {
                Glide.with(binding.root.context)
                    .load(item.prof.img)
                    .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(50)))
                    .into(binding.tipIcon)
            }

            binding.executePendingBindings()
            binding.clickListener = clickListener

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


class TipListDiffCallback: DiffUtil.ItemCallback<TipWithUser>() {
    override fun areItemsTheSame(oldItem: TipWithUser, newItem: TipWithUser): Boolean {
        return oldItem.tips!!.idx == newItem.tips!!.idx
    }

    override fun areContentsTheSame(oldItem: TipWithUser, newItem: TipWithUser): Boolean {
        return oldItem == newItem
    }

}

sealed class DataItem {

    abstract val id:Long

    data class ResultItem(val tip:TipWithUser):DataItem() {
        override val id = tip.tips!!.idx
    }

    object Header:DataItem() {
        override val id = Long.MIN_VALUE
    }
}

