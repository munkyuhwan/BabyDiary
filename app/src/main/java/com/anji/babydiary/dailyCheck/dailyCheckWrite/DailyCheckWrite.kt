package com.anji.babydiary.dailyCheck.dailyCheckWrite

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.anji.babydiary.R
import com.anji.babydiary.common.CommonCode
import com.anji.babydiary.common.MyShare.MyShare
import com.anji.babydiary.common.Utils
import com.anji.babydiary.dailyCheck.listAdapter.DailyCheckListAdapter
import com.anji.babydiary.database.dailyCheck.DailyCheckDatabase
import com.anji.babydiary.databinding.DailyCheckWriteFragmentBinding

class DailyCheckWrite : Fragment() {


    private lateinit var viewModel: DailyCheckWriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val idx = MyShare.prefs.getLong("idx", 0L)

        Log.e("data","${ Utils.getDate(System.currentTimeMillis(), "YYYY - MM - d E")} ")


        var year = "${ Utils.getDate(System.currentTimeMillis(), "YYYY")}"
        var month = "${ Utils.getDate(System.currentTimeMillis(), "MM")}"
        var date = "${ Utils.getDate(System.currentTimeMillis(), "dd")}"
        var day = "${ Utils.getDate(System.currentTimeMillis(), "E")}"

        when(day) {
            "월" -> {
                day = "MON"
            }
            "화" -> {
                day = "TUE"

            }
            "수" -> {
                day = "WED"

            }
            "목" -> {
                day = "THU"

            }
            "금" -> {
                day = "FRI"

            }
            "토" -> {
                day = "SAT"

            }
            "일" -> {
                day = "SUN"

            }
        }


        if (requireArguments().size() <= 0) {

        }else {
            val arg = arguments?.let {
                DailyCheckWriteArgs.fromBundle(it)
            }

            arg?.let {
                year = it.year.toString()
                month = it.month.toString()
                date = it.date.toString()
                day = it.day.toString()
            }


        }

        val binding = DataBindingUtil.inflate<DailyCheckWriteFragmentBinding>(
            inflater,
            R.layout.daily_check_write_fragment,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val database = DailyCheckDatabase.getInstance(application).database

        val viewModelFactory = DailyCheckWriteViewModelFactory(database, idx, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DailyCheckWriteViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.writeViewModel = viewModel

        viewModel.selectedYear.value = year
        viewModel.selectedMonth.value = month
        viewModel.selectedDate.value = date
        viewModel.selectedDay.value = day


        val listAdapter = DailyCheckListAdapter(true)
        binding.recordList.adapter = listAdapter

        viewModel.dataToday.observe(viewLifecycleOwner, Observer {

            it?.let{
                listAdapter.submitList(it)
                if (it.size > 0) {
                    binding.recordList.background = resources.getDrawable(R.drawable.top_left_corner_white)
                }

            }
        })

        binding.dailyCheckBacnBtn.setOnClickListener {
            requireActivity().finish()
        }

        binding.selectedMonth.setOnClickListener {
            findNavController().navigate(DailyCheckWriteDirections.actionDailyCheckWriteToDailyCheckCalendar22())
        }
        binding.selectedDate.setOnClickListener {
            findNavController().navigate(DailyCheckWriteDirections.actionDailyCheckWriteToDailyCheckCalendar22())
        }
        binding.selectedDay.setOnClickListener {
            findNavController().navigate(DailyCheckWriteDirections.actionDailyCheckWriteToDailyCheckCalendar22())
        }

        return binding.root
    }


}