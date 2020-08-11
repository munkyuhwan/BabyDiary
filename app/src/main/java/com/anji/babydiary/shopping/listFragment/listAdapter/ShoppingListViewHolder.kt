package com.anji.babydiary.shopping.listFragment.listAdapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anji.babydiary.R

class ShoppingListViewHolder (itemView: View):RecyclerView.ViewHolder(itemView) {

    val img:ImageView = itemView.findViewById(R.id.product_image)
    val price:TextView = itemView.findViewById(R.id.product_price)
}