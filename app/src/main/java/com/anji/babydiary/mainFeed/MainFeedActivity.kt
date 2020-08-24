package com.anji.babydiary.mainFeed

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.anji.babydiary.R
import com.anji.babydiary.common.BaseActivity
import com.anji.babydiary.common.dailyCheck.DailyCheckViewModel
import com.anji.babydiary.common.dailyCheck.DailyCheckViewModelFactory
import com.anji.babydiary.databinding.ActivityMainFeedBinding
import kotlinx.android.synthetic.main.daily_check_calendar.view.*
import kotlinx.android.synthetic.main.daily_check_detail.view.*
import java.util.*

class MainFeedActivity() : BaseActivity() {
    private lateinit var binding:ActivityMainFeedBinding
    private lateinit var appBarConfiguration: AppBarConfiguration.Builder

    private lateinit var viewModel: MainFeedViewModel
    private lateinit var viewModelFactory: MainFeedViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_feed)

        val application = requireNotNull(this).application

        viewModelFactory = MainFeedViewModelFactory(application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainFeedViewModel::class.java)

        binding.lifecycleOwner = this
        binding.feedMain = viewModel

        val navViewModel = setNav(R.id.feedNestedHost)
        binding.navController = navViewModel

        navViewModel.isMain.value = true

        //setOnclickMenu()

        dailyCheckDrawerSetting()

        binding.bottomNav = setBottomNav(2)


        supportActionBar!!.setDisplayShowCustomEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        supportActionBar!!.setHomeAsUpIndicator(null)
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


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.feedNestedHost)
        return navController.navigateUp()
    }

}