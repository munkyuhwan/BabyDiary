package com.anji.babydiary.dailyCheck.listAdapter

import android.app.Activity
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anji.babydiary.common.Utils
import com.anji.babydiary.dailyCheck.DailyCheckListObj
import com.anji.babydiary.dailyCheck.DailyCheckViewModel
import com.anji.babydiary.dailyCheck.DailyCheckViewModelFactory
import com.anji.babydiary.dailyCheck.dailyCheckWrite.EditClickListener
import com.anji.babydiary.database.dailyCheck.DailyCheck
import com.anji.babydiary.database.dailyCheck.DailyCheckDatabase
import com.anji.babydiary.databinding.DailyCheckListAdapterBinding
import kotlinx.coroutines.*

class DailyCheckListAdapter(val isDetail:Boolean, val editClickListener:EditClickListener, val fragment:Fragment):ListAdapter<DailyCheck, DailyCheckListAdapter.DailyCheckViewHolder>(DailyCHeckListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyCheckViewHolder {
        return DailyCheckListAdapter.DailyCheckViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: DailyCheckViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, isDetail, editClickListener, fragment)
    }

    class DailyCheckViewHolder private constructor(val binding:DailyCheckListAdapterBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(item:DailyCheck, isDetail: Boolean, editClickListener:EditClickListener, fragment:Fragment){

            binding.dailyCheck = item
            binding.checkCategory.text = DailyCheckListObj.itemName[item.category]
            binding.editClick = editClickListener

            val database = DailyCheckDatabase.getInstance(fragment.requireContext()).database

            val viewModelFactory = DailyCheckListAdapterViewModelFactory(database, fragment.requireActivity().application)
            val vm = ViewModelProviders.of(fragment, viewModelFactory).get(DailyCheckListAdapterViewModel::class.java)
            binding.viewModel = vm

            if (item.valueTwo!="") {
                binding.checkText.text = "${Utils.convertToTime(item.valueOne)} ${Utils.convertToTime(item.valueTwo)}"
            }else {
                binding.checkText.text = "${item.valueOne}"
            }
            if (!isDetail) {
                binding.editRecordBtn.visibility = View.GONE
                binding.checkText.setTextColor( Color.parseColor("#ffffff") )
                binding.checkTime.setTextColor( Color.parseColor("#ffffff") )
                binding.checkCategory.setTextColor( Color.parseColor("#ffffff") )
            }

            binding.checkTime.text = "${item.hour}:${item.minute}"
            binding.checkIcon.setBackgroundResource(DailyCheckListObj.itemBackground[item.category])
            binding.checkIcon.setImageResource(DailyCheckListObj.itemSrc[item.category])

            binding.editRecordBtn.setOnClickListener {
                binding.checkCategory.visibility = View.GONE
                binding.checkText.visibility = View.GONE
                binding.checkTime.visibility = View.GONE
                if (item.valueTwo!="") {
                    binding.rightTime.visibility = View.VISIBLE
                    binding.leftTime.visibility = View.VISIBLE
                    binding.checkMemo.visibility = View.GONE
                }else {
                    binding.rightTime.visibility = View.GONE
                    binding.leftTime.visibility = View.GONE
                    binding.checkMemo.visibility = View.VISIBLE
                }
                binding.inputDataWrapper.visibility = View.VISIBLE
            }

            binding.completeBtn.setOnClickListener {
                binding.checkCategory.visibility = View.VISIBLE
                binding.checkText.visibility = View.VISIBLE
                binding.checkTime.visibility = View.VISIBLE
                binding.inputDataWrapper.visibility = View.GONE
                if (item.valueTwo!="") {
                    vm.completeEdit(item.idx, binding.leftTime.text.toString(),binding.rightTime.text.toString())
                }else {
                    vm.completeEdit(item.idx, binding.checkMemo.text.toString(),"")
                }
            }

            binding.leftTime.setOnClickListener {
                startCounting(true, fragment)
            }

            binding.rightTime.setOnClickListener {
                startCounting(false, fragment)
            }

            binding.executePendingBindings()

        }

        companion object {
            fun from(parent: ViewGroup):DailyCheckViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DailyCheckListAdapterBinding.inflate(layoutInflater, parent, false)
                return DailyCheckViewHolder(binding)
            }

            var leftCounting:Int = 0
            var rightCounting:Int = 0
            var isLeftCountingStarted:Boolean = true
            var isRightCountingStarted:Boolean = true

            val job = Job()
            val uiScope = CoroutineScope(Dispatchers.Main + job)

        }


        fun startCounting(isLeft:Boolean, fragment: Fragment) {
            if (isLeft && leftCounting!! > 0 ) {
                Log.e("is left couting:" ,"${isLeftCountingStarted!!}")
                if (isLeftCountingStarted!!) {
                    stopCounting(isLeft)
                }else {
                    isLeftCountingStarted = true
                    uiScope.launch {
                        count(isLeft,fragment)
                    }
                }
            }else {
                uiScope.launch {
                    count(isLeft,fragment)
                }
            }

            if(!isLeft && rightCounting!! > 0){
                if (isRightCountingStarted!!) {
                    stopCounting(isLeft)
                }else {
                    isRightCountingStarted = true
                    uiScope.launch {
                        count(isLeft,fragment)
                    }
                }
            }else {
                uiScope.launch {
                    count(isLeft,fragment)
                }
            }
        }

        suspend fun count(isLeft:Boolean, fragment: Fragment) {
            withContext(Dispatchers.IO) {
                if (isLeft) {
                    while (isLeftCountingStarted!!) {
                        Thread.sleep(1000)
                        leftCounting = leftCounting + 1
                        fragment.requireActivity().runOnUiThread {
                            binding.leftTime.text = leftCounting.toString()
                        }
                        Log.e("count","================================================================================")
                        Log.e("count","count: ${leftCounting}")
                        Log.e("count","================================================================================")
                    }
                }else {
                    while (isRightCountingStarted) {
                        Thread.sleep(1000)
                        rightCounting = (rightCounting + 1)
                        fragment.requireActivity().runOnUiThread {
                            binding.rightTime.text = rightCounting.toString()
                        }
                    }
                }
            }

        }

        fun stopCounting(isLeft:Boolean) {
            if (isLeft) {
                isLeftCountingStarted = false
            }else {
                isRightCountingStarted = false
            }

        }

    }


}

class DailyCHeckListDiffCallback: DiffUtil.ItemCallback<DailyCheck>() {
    override fun areItemsTheSame(oldItem: DailyCheck, newItem: DailyCheck): Boolean {
        return oldItem.idx == newItem.idx
    }

    override fun areContentsTheSame(oldItem: DailyCheck, newItem: DailyCheck): Boolean {
        return oldItem == newItem
    }

}




