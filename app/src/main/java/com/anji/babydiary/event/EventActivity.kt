package com.anji.babydiary.event

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProviders
import com.anji.babydiary.R
import com.anji.babydiary.common.BaseActivity
import com.anji.babydiary.databinding.ActivityEventBinding
import kotlinx.android.synthetic.main.daily_check_calendar.view.*
import java.util.*

class EventActivity : BaseActivity() {


    private lateinit var binding:ActivityEventBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_event)

        binding = DataBindingUtil.setContentView<ActivityEventBinding>(this, R.layout.activity_event)

        val application = requireNotNull(this).application

        val viewModelFactory = EventViewModelFactory(application)
        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(EventViewModel::class.java)
        binding.eventViewModel = viewModel

        setNav(R.id.eventNestFragment)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->

            Log.e("nav","============================================")
            Log.e("nav","${destination.label}")

            when(destination.label.toString()) {
                "EventList" ->
                    toolbar.setBackgroundResource(R.drawable.actionbar_transparent)
                "EventDetail" ->
                    toolbar.setBackgroundResource(R.drawable.actionbar_event)

            }

            Log.e("nav","============================================")

        }

        //setOnclickMenu()
        dailyCheckDrawerSetting()
        binding.bottomNav = setBottomNav(0)

    }

    private fun dailyCheckDrawerSetting() {
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        var dailycheckViewModel = setDailyCheckViewModel()
        binding.dailyCheckViewModel = dailycheckViewModel


        binding.drawerInc.drawerWrapper.calendarView.setOnDateChangeListener { calendarView, y, m, d ->
            val calendar = Calendar.getInstance()
            calendar[y, m] = d
            val dayOfWeek = calendar[Calendar.DAY_OF_WEEK]

            dailycheckViewModel.selectedMonth.value = m.toString()
            dailycheckViewModel.selectedDate.value = ".${d}"
            dailycheckViewModel.onDaySelect(dayOfWeek)
        }


        binding.fab.setOnClickListener {
            binding.drawerLayout.openDrawer(Gravity.LEFT)
        }

    }



}