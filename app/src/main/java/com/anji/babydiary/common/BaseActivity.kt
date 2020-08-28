package com.anji.babydiary.common

import android.content.Intent
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.anji.babydiary.R
import com.anji.babydiary.common.bottomNavigation.BottomNavigationViewModel
import com.anji.babydiary.common.bottomNavigation.BottomNavigationViewModelFactory
import com.anji.babydiary.dailyCheck.DailyCheckViewModel
import com.anji.babydiary.dailyCheck.DailyCheckViewModelFactory
import com.anji.babydiary.database.dailyCheck.DailyCheckDatabase
import com.anji.babydiary.event.EventActivity
import com.anji.babydiary.gnb.main.NavViewModel
import com.anji.babydiary.gnb.main.NavViewModelFactory
import com.anji.babydiary.mainFeed.MainFeedActivity
import com.anji.babydiary.myPage.MyPage
import com.anji.babydiary.shopping.ShoppingActivity
import com.anji.babydiary.tips.TipActivity
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

abstract class BaseActivity() : AppCompatActivity() {

    lateinit var toolbar:Toolbar;
    lateinit var navController:NavController
    lateinit var navViewModel: NavViewModel

    fun setNav(nestedHost:Int):NavViewModel {
        var appBarConfiguration: AppBarConfiguration.Builder

        val navViewModelFactory = NavViewModelFactory(application)
        navViewModel = ViewModelProviders.of(this, navViewModelFactory).get(NavViewModel::class.java)


        navController = this.findNavController(nestedHost)
        val layout = findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar_layout)
        toolbar = findViewById<Toolbar>(R.id.activity_toolbar)


        setSupportActionBar(toolbar)


        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(null)

        appBarConfiguration = AppBarConfiguration.Builder(navController.graph)
        layout.isTitleEnabled=false
        layout.setupWithNavController(toolbar, navController, appBarConfiguration.build())


        navViewModel.isOpen.observe(this, Observer {
            //layout.nav_category.bringToFront()
        })
        return navViewModel
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



    fun dailyCheckDrawerSetting(
        drawerLayout:DrawerLayout,
        drawerWrapper:ConstraintLayout,
        fab:FloatingActionButton,
        dailyCheckViewModel: DailyCheckViewModel
        )
    {
        //drawerlayout, dailyCheckVIewModel, drawerWrapper, fab

        /*
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)


        drawerWrapper.calendarView.setOnDateChangeListener { calendarView, y, m, d ->
            val calendar = Calendar.getInstance()
            calendar[y, m] = d
            val dayOfWeek = calendar[Calendar.DAY_OF_WEEK]

            dailyCheckViewModel.selectedYear.value = y.toString()
            dailyCheckViewModel.selectedMonth.value = m.toString()
            dailyCheckViewModel.selectedDate.value = d.toString()
            dailyCheckViewModel.onDaySelect(dayOfWeek)
        }
        fab.setOnClickListener {
            //drawerLayout.openDrawer(Gravity.LEFT)
        }

         */

    }


}