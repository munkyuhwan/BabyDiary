package com.anji.babydiary.tips

import android.os.Bundle
import android.view.Gravity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.ui.AppBarConfiguration
import com.anji.babydiary.R
import com.anji.babydiary.common.BaseActivity
import com.anji.babydiary.databinding.ActivityTipBinding
import kotlinx.android.synthetic.main.daily_check_calendar.view.*
import java.util.*

class TipActivity : BaseActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration.Builder
    private lateinit var viewModel: TipActivityViewModel
    private lateinit var binding: ActivityTipBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_tip)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_tip)
        viewModel = TipActivityViewModel(application)
        binding.tipViewModel = viewModel

        setNav(R.id.tipNestedHost)
        //setOnclickMenu()

        dailyCheckDrawerSetting()

        binding.bottomNav = setBottomNav(3)
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