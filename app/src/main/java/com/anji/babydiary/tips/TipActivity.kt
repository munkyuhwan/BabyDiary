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

        var dailycheckViewModel = setDailyCheckViewModel()
        binding.dailyCheckViewModel = dailycheckViewModel

        dailyCheckDrawerSetting(
            binding.drawerLayout,
            binding.drawerInc.drawerWrapper,
            binding.fab,
            dailycheckViewModel
        )
        binding.bottomNav = setBottomNav(3)
    }


}