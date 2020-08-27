package com.anji.babydiary.dailyCheck.dailyCheckWrite

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.anji.babydiary.R
import com.anji.babydiary.dailyCheck.DailyCheckViewModel
import com.anji.babydiary.dailyCheck.DailyCheckViewModelFactory
import com.anji.babydiary.database.dailyCheck.DailyCheckDatabase
import com.anji.babydiary.databinding.DailyCheckWriteFragmentBinding
import com.anji.babydiary.mainFeed.feedDetail.FeedDetailArgs

class DailyCheckWrite : Fragment() {

    companion object {
        fun newInstance() = DailyCheckWrite()
    }

    private lateinit var viewModel: DailyCheckWriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val arg = arguments?.let { DailyCheckWriteArgs.fromBundle(it) }
        var year = arg!!.year
        var month = arg!!.month
        var date = arg!!.date
        var day = arg!!.day

        val binding = DataBindingUtil.inflate<DailyCheckWriteFragmentBinding>(
            inflater,
            R.layout.daily_check_write_fragment,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val database = DailyCheckDatabase.getInstance(application).database

        val viewModelFactory = DailyCheckWriteViewModelFactory(database, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DailyCheckWriteViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.writeViewModel = viewModel

        viewModel.selectedYear.value = year
        viewModel.selectedMonth.value = month
        viewModel.selectedDate.value = date
        viewModel.selectedDay.value = day


        viewModel.dataToday.observe(viewLifecycleOwner, Observer {

            Log.e("dateData","==============data================================")
            Log.e("dateData","${it}")
            Log.e("dateData","==============data================================")
        })

        viewModel.selectedData.observe(viewLifecycleOwner, Observer {
            viewModel.insertUpdateData()
        })


        return binding.root
    }


}