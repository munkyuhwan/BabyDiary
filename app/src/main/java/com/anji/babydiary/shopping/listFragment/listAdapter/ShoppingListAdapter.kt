package com.anji.babydiary.shopping.listFragment.listAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anji.babydiary.database.mainFeed.MainFeed
import com.anji.babydiary.database.shopping.Shopping
import com.anji.babydiary.databinding.ProductItemBinding
import com.anji.babydiary.databinding.ShopListFragmentBinding
import com.anji.babydiary.shopping.listFragment.ProductClickListener
import com.bumptech.glide.Glide

class ShoppingListAdapter(val clickListener: ProductClickListener):ListAdapter<Shopping, ShoppingListAdapter.ViewHolder>(ShoppingListDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = getItem(position!!)

        holder.bind(item, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind (item: Shopping, clickListener: ProductClickListener) {
            //binding.idx = item
            //binding.productImage.setImageResource(item.imgDir)
            Glide.with(binding.root.context).load(item.imgDir).into(binding.productImage)
            binding.productPrice.text = item.price.toString()

            binding.executePendingBindings()
            binding.clickListener = clickListener

        }

        companion object {
            fun from(parent: ViewGroup):ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ProductItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }

        }

    }



}


class ShoppingListDiffCallback: DiffUtil.ItemCallback<Shopping>() {
    override fun areItemsTheSame(oldItem: Shopping, newItem: Shopping): Boolean {
        return oldItem.idx == newItem.idx
    }

    override fun areContentsTheSame(oldItem: Shopping, newItem: Shopping): Boolean {
        return oldItem == newItem
    }

}

sealed class DataItem {

    abstract val id:Long

    data class ProductItem(val gameResult:MainFeed):DataItem() {
        override val id = gameResult.idx
    }

    object Header:DataItem() {
        override val id = Long.MIN_VALUE
    }
}
