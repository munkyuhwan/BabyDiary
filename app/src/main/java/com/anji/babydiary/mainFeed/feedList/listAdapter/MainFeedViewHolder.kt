package com.anji.babydiary.mainFeed.feedList.listAdapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anji.babydiary.R

class MainFeedViewHolder (itemView:View):RecyclerView.ViewHolder(itemView) {

    val userIcon:ImageView = itemView.findViewById(R.id.user_icon)
    val userId:TextView = itemView.findViewById(R.id.user_id)
    val feedImage:ImageView = itemView.findViewById(R.id.feed_img)
    val likeCnt:TextView = itemView.findViewById(R.id.like_cnt)
    val mainText:TextView = itemView.findViewById(R.id.main_feed_text)


}