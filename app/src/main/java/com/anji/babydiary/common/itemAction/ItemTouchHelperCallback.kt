package com.anji.babydiary.common.itemAction

import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.anji.babydiary.tips.tipsComment.TipsCommentListAdapter


class ItemTouchHelperCallback(val listener: TipsCommentListAdapter) : ItemTouchHelper.Callback() {


    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.DOWN or ItemTouchHelper.UP
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(dragFlags, swipeFlags)    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {

        //listener.onItemMoved(viewHolder!!.adapterPosition, target!!.adapterPosition)
        return true    }

    override fun isLongPressDragEnabled(): Boolean = false
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        //listener.onItemSwiped(viewHolder!!.adapterPosition)
        Log.e("swipqe","==================swiped!!======================================")

    }
}