package com.anji.babydiary.myPage.myFamily.listAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anji.babydiary.database.family.Family
import com.anji.babydiary.databinding.MyFamilyListItemBinding
import com.anji.babydiary.databinding.MyFeedListItemBinding
import com.anji.babydiary.myPage.myFeed.myFeedListAdapter.MyFeedListAdapter

class FamilyListAdapter() : ListAdapter<Family, FamilyListAdapter.FamilyViewHolder>(FamilyListDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FamilyViewHolder {
        return FamilyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FamilyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }



    class FamilyViewHolder private constructor(val binding: MyFamilyListItemBinding): RecyclerView.ViewHolder(binding.root)  {

        fun bind(item:Family) {

            binding.familyTitle.text = item.family_title.toString()
            binding.familyText.text = item.family_name.toString()

        }

        companion object {
            fun from(parent:ViewGroup):FamilyViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MyFamilyListItemBinding.inflate(layoutInflater, parent, false)
                return FamilyListAdapter.FamilyViewHolder(binding)
            }
        }

    }

}

class FamilyListDiffUtilCallback: DiffUtil.ItemCallback<Family>() {
    override fun areItemsTheSame(oldItem: Family, newItem: Family): Boolean {
        return oldItem.idx == newItem.idx
    }

    override fun areContentsTheSame(oldItem: Family, newItem: Family): Boolean {
        return oldItem == newItem
    }

}

