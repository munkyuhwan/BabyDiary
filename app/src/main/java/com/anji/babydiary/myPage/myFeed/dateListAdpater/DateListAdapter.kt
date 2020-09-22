package com.anji.babydiary.myPage.myFeed.dateListAdpater

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anji.babydiary.common.CommonCode
import com.anji.babydiary.common.MyShare.MyShare
import com.anji.babydiary.database.mainFeed.MainFeed
import com.anji.babydiary.database.mainFeed.MainFeedDAO
import com.anji.babydiary.databinding.DateListItemBinding
import com.anji.babydiary.myPage.myFeed.MyFeedClickListener
import com.anji.babydiary.myPage.myFeed.MyFeedDirections
import com.anji.babydiary.myPage.myFeed.myFeedListAdapter.MyFeedListAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class DateListAdapter(val clickListener:MyFeedClickListener, val activity:Activity, val database:MainFeedDAO, val navController: NavController, val lifecycleOwner: LifecycleOwner) : ListAdapter<MainFeed, DateListAdapter.DateViewHolder>(MyFeedDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        return DateViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener, activity, database,navController, lifecycleOwner)
    }

    class DateViewHolder private constructor(val binding: DateListItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MainFeed,  clickListener:MyFeedClickListener,  activity:Activity, database:MainFeedDAO , navController: NavController, lifecycleOwner:LifecycleOwner) {

            val monthName = arrayOf(
                "",
                "JAN",
                "FEB",
                "MAR",
                "APR",
                "MAY",
                "JUN",
                "JUL",
                "AUG",
                "SEP",
                "OCT",
                "NOV",
                "DEC"
            )

            binding.date.text = item.date.toString()
            binding.month.text = monthName[item.month]
            binding.year.text = item.year.toString()


            val clickAdapter = MyFeedListAdapter(MyFeedClickListener {
                it?.let {
                    navController.navigate(MyFeedDirections.actionMyFeedToMyFeedDetail(it))
                }
            }, activity)
            binding.myFeedList.adapter = clickAdapter
            val manager = GridLayoutManager(activity,3)
            binding.myFeedList.layoutManager = manager

            val allData = database.selectByUserIdxANDDate(item.userIdx, item.date, item.month, item.year)
            Log.e("allData","===========================================================================")
            Log.e("allData","${allData.value}")
            Log.e("allData","===========================================================================")
            allData.observe(lifecycleOwner, Observer {
                clickAdapter.submitList(it)
            })

            binding.executePendingBindings()
        }


        companion object {
            fun from(parent: ViewGroup):DateViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DateListItemBinding.inflate(layoutInflater, parent, false)

                return DateViewHolder(binding)
            }
        }

    }


}

class MyFeedDiffUtilCallback: DiffUtil.ItemCallback<MainFeed>() {
    override fun areItemsTheSame(oldItem: MainFeed, newItem: MainFeed): Boolean {
        return oldItem.idx == newItem.idx
    }

    override fun areContentsTheSame(oldItem: MainFeed, newItem: MainFeed): Boolean {
        return oldItem == newItem
    }

}


