package com.anji.babydiary.shopping

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.ui.AppBarConfiguration
import com.anji.babydiary.common.BaseActivity
import com.anji.babydiary.R
import com.anji.babydiary.databinding.ActivityShoppingBinding
import kotlinx.android.synthetic.main.daily_check_calendar.view.*
import java.util.*

class ShoppingActivity() : BaseActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration.Builder
    private lateinit var viewModel:ShopActivityViewModel
    private lateinit var binding:ActivityShoppingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_shopping)
        viewModel = ShopActivityViewModel(application)
        binding.shopMain = viewModel

        setNav(R.id.shoppingNestedHost)

        binding.bottomNav = setBottomNav(1)
        dailyCheckDrawerSetting()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


    }

}