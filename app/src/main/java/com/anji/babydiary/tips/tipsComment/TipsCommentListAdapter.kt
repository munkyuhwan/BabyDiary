package com.anji.babydiary.tips.tipsComment

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.*
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.View.OnTouchListener
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anji.babydiary.common.itemAction.ItemActionListener
import com.anji.babydiary.common.itemAction.ItemTouchHelperCallback
import com.anji.babydiary.database.profile.ProfileDao
import com.anji.babydiary.database.profile.ProfileDatabase
import com.anji.babydiary.database.profile.Profiles
import com.anji.babydiary.database.tip.tipsComment.TipsComment
import com.anji.babydiary.databinding.CommentListItemBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.*


class TipsCommentListAdapter(val activity:Activity, val lifecycleOwner: LifecycleOwner, val editClicked: TipEditClicked, val deleteClicked: TipDeleteClicked): ListAdapter<TipsComment, TipsCommentListAdapter.ViewHolder>(TipsCommentListDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)!!
        val res = holder.itemView.context.resources


        holder.bind(item, activity, lifecycleOwner, editClicked, deleteClicked)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder.from(parent)
    }


    class ViewHolder (val binding: CommentListItemBinding) : RecyclerView.ViewHolder(binding.root), ItemActionListener {
        override fun onItemMoved(from: Int, to: Int) {

        }

        override fun onItemSwiped(position: Int) {

        }

        fun bind (item:TipsComment, activity:Activity, lifecycleOwner: LifecycleOwner, editClicked: TipEditClicked, deleteClicked: TipDeleteClicked) {
            //binding.idx = item


            binding.tipsComment = item
            binding.editClick = editClicked
            binding.deleteClick = deleteClicked

            binding.commentText.text = item.commentText.toString()

            val profileDatabase = ProfileDatabase.getInstance(activity.applicationContext).database
            val job = Job()
            val uiScope = CoroutineScope(Dispatchers.Main + job)
            val profileData = MutableLiveData<Profiles>()



            profileData.observe(lifecycleOwner, Observer {

                binding.userId.text = "${it.name}"

                if(it.imgTmp != "") {
                    Glide.with(binding.root.context)
                        .load(activity.resources.getIdentifier(it.imgTmp, "drawable", activity.packageName))
                        .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(50)))
                        .into(binding.userIcon)
                    binding.executePendingBindings()
                }else {
                    Glide.with(binding.root.context)
                        .load(it.img)
                        .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(50)))
                        .into(binding.userIcon)
                    binding.executePendingBindings()
                }
            })

            uiScope.launch {
                setData(profileData, profileDatabase, item)
            }

            binding.executePendingBindings()

        }


        suspend fun setData(
            profileData: MutableLiveData<Profiles>,
            profileDatabase: ProfileDao,
            item: TipsComment
        ) {
            withContext(Dispatchers.IO) {
                profileData.postValue(profileDatabase.selectProfileData(item.userIdx))
            }
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


class TipsCommentListDiffCallback: DiffUtil.ItemCallback<TipsComment>() {
    override fun areItemsTheSame(oldItem: TipsComment, newItem: TipsComment): Boolean {
        return oldItem.idx == newItem.idx
    }

    override fun areContentsTheSame(oldItem: TipsComment, newItem: TipsComment): Boolean {
        return oldItem == newItem
    }

}

sealed class DataItem {

    abstract val id:Long

    data class ResultItem(val gameResult:TipsComment):DataItem() {
        override val id = gameResult.idx
    }

    object Header:DataItem() {
        override val id = Long.MIN_VALUE
    }
}

class OnSwipeTouchListener(ctx: Context?) : OnTouchListener {
    companion object {
        private const val SWIPE_THRESHOLD = 100
        private const val SWIPE_VELOCITY_THRESHOLD = 100
    }
    private val gestureDetector: GestureDetector
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    private inner class GestureListener : SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            var result = false
            try {
                val diffY = e2.y - e1.y
                val diffX = e2.x - e1.x
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > Companion.SWIPE_THRESHOLD && Math.abs(
                            velocityX
                        ) > Companion.SWIPE_VELOCITY_THRESHOLD
                    ) {
                        if (diffX > 0) {
                            onSwipeRight()
                        } else {
                            onSwipeLeft()
                        }
                    }
                    result = true
                } else if (Math.abs(diffY) > Companion.SWIPE_THRESHOLD && Math.abs(
                        velocityY
                    ) > Companion.SWIPE_VELOCITY_THRESHOLD
                ) {
                    if (diffY > 0) {
                        onSwipeBottom()
                    } else {
                        onSwipeTop()
                    }
                }
                result = true
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
            return result
        }


    }

    fun onSwipeRight() {}
    fun onSwipeLeft() {}
    fun onSwipeTop() {}
    fun onSwipeBottom() {}

    init {
        gestureDetector = GestureDetector(ctx, GestureListener())
    }
}



