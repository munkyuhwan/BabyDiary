package com.anji.babydiary.dailyCheck.dailyCheckCalendar

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.anji.babydiary.R
import com.anji.babydiary.dailyCheck.DailyCheckViewModel
import com.anji.babydiary.dailyCheck.DailyCheckViewModelFactory
import com.anji.babydiary.database.dailyCheck.DailyCheckDatabase
import com.anji.babydiary.databinding.ActivityDailyCheckBinding
import com.anji.babydiary.databinding.DailyCheckCalendarFragmentBinding
import java.util.*

class DailyCheckCalendar : Fragment() {


    private lateinit var viewModel: DailyCheckCalendarViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<DailyCheckCalendarFragmentBinding>(inflater, R.layout.daily_check_calendar_fragment, container, false)

        val application = requireNotNull(activity).application
        val database = DailyCheckDatabase.getInstance(application).database


        val viewModelFactory = DailyCheckCalendarViewModelFactory(database, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DailyCheckCalendarViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.calendarViewModel = viewModel




        binding.calendarView.setOnDateChangeListener { calendarView, y, m, d ->
            val calendar = Calendar.getInstance()
            calendar[y, m] = d
            val dayOfWeek = calendar[Calendar.DAY_OF_WEEK]
            viewModel.selectedYear.value = y.toString()
            viewModel.selectedMonth.value = m.toString()
            viewModel.selectedDate.value = d.toString()
            viewModel.onDaySelect(dayOfWeek)
        }

        binding.calendarDetailWrapper.setOnClickListener {
            findNavController().navigate(DailyCheckCalendarDirections.actionDailyCheckCalendar2ToDailyCheckWrite(viewModel.selectedYear.value.toString(), viewModel.selectedMonth.value.toString(), viewModel.selectedDate.value.toString()) )
        }


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DailyCheckCalendarViewModel::class.java)
        // TODO: Use the ViewModel
    }

}