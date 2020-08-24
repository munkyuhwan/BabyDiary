package com.anji.babydiary.common

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.anji.babydiary.R
import com.anji.babydiary.backgroudnViewModel.BackgroundViewModel
import com.anji.babydiary.backgroudnViewModel.BackgroundViewModelFactory
import com.anji.babydiary.common.bottomNavigation.BottomNavigationViewModel
import com.anji.babydiary.common.bottomNavigation.BottomNavigationViewModelFactory
import com.anji.babydiary.common.dailyCheck.DailyCheckViewModel
import com.anji.babydiary.common.dailyCheck.DailyCheckViewModelFactory
import com.anji.babydiary.databinding.ActivityMyPageBinding
import com.anji.babydiary.databinding.DailyCheckCalendarBinding
import com.anji.babydiary.databinding.DailyCheckDrawerBinding
import com.anji.babydiary.databinding.GnbLayoutBinding
import com.anji.babydiary.event.EventActivity
import com.anji.babydiary.gnb.main.NavViewModel
import com.anji.babydiary.gnb.main.NavViewModelFactory
import com.anji.babydiary.gnb.myPage.MyPageNavViewModel
import com.anji.babydiary.gnb.myPage.MyPageNavViewModelFactory
import com.anji.babydiary.mainFeed.MainFeedActivity
import com.anji.babydiary.myPage.MyPage
import com.anji.babydiary.shopping.ShoppingActivity
import com.anji.babydiary.tips.TipActivity
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView

abstract class BaseActivity() : AppCompatActivity() {

    lateinit var toolbar:Toolbar;
    lateinit var navController:NavController
    lateinit var navViewModel: NavViewModel

    fun setNav(nestedHost:Int):NavViewModel {
        var appBarConfiguration: AppBarConfiguration.Builder

        val navViewModelFactory = NavViewModelFactory(application)
        navViewModel = ViewModelProviders.of(this, navViewModelFactory).get(NavViewModel::class.java)
        //val navBinding = DataBindingUtil.setContentView<DailyCheckCalendarBinding>(this, R.layout.daily_check_calendar)
        //navBinding.navController = navViewModel

        //binding.navController = navViewModel

        navController = this.findNavController(nestedHost)
        val layout = findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar_layout)
        toolbar = findViewById<Toolbar>(R.id.activity_toolbar)


        setSupportActionBar(toolbar)

        //if (actionBar != null) {


        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(null)
        //actionBar.setHomeAsUpIndicator(R.drawable.back_btn)
        //}

        appBarConfiguration = AppBarConfiguration.Builder(navController.graph)
        layout.isTitleEnabled=false
        layout.setupWithNavController(toolbar, navController, appBarConfiguration.build())


        navViewModel.isOpen.observe(this, Observer {
            //layout.nav_category.bringToFront()
        })
        return navViewModel
    }

    fun setDailyCheckViewModel():DailyCheckViewModel {
        val dailyCheckViewModelFactory = DailyCheckViewModelFactory(this)
        val dailyCheckViewModel = ViewModelProviders.of(this, dailyCheckViewModelFactory).get(
            DailyCheckViewModel::class.java)

        return dailyCheckViewModel
    }

    fun setBottomNav(idx:Int):BottomNavigationViewModel {
        val bottomNavViewModelFactory = BottomNavigationViewModelFactory(this, idx)
        val bottomNavViewModel = ViewModelProviders.of(this, bottomNavViewModelFactory).get(BottomNavigationViewModel::class.java)
        return bottomNavViewModel
    }


    fun eventIntent() {
        val intent: Intent = Intent(this, EventActivity::class.java)
        startActivity(intent)
    }

    fun tipIntent() {
        val intent: Intent = Intent(this, TipActivity::class.java)
        startActivity(intent)
    }
    fun shoppingIntent() {

        val intent: Intent = Intent(this, ShoppingActivity::class.java)
        startActivity(intent)

    }

    fun mainIntent() {

        val intent: Intent = Intent(this, MainFeedActivity::class.java)
        startActivity(intent)

    }

    fun myPageIntent() {
        val intent: Intent = Intent(this, MyPage::class.java)
        startActivity(intent)

    }

    fun bindGNB() {

    }




}