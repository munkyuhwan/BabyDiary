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

        var dailycheckViewModel = setDailyCheckViewModel()
        binding.dailyCheckViewModel = dailycheckViewModel

        dailyCheckDrawerSetting(
            binding.drawerLayout,
            binding.drawerInc.drawerWrapper,
            binding.fab,
            dailycheckViewModel
        )
        binding.bottomNav = setBottomNav(1)

    }


}