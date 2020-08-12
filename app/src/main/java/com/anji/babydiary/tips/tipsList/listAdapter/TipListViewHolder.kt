package com.anji.babydiary.tips.tipsList.listAdapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anji.babydiary.R

class TipListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val tipUserId: TextView = itemView.findViewById(R.id.tip_user_id)
    val tipImage: ImageView = itemView.findViewById(R.id.tipImgView)
    val tipLikeCnt: TextView = itemView.findViewById(R.id.tip_like_cnt)
    val tipMainText: TextView = itemView.findViewById(R.id.tip_text)
}