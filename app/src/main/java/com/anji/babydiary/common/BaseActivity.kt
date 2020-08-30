package com.anji.babydiary.common

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.anji.babydiary.R
import com.anji.babydiary.common.bottomNavigation.BottomNavigationViewModel
import com.anji.babydiary.common.bottomNavigation.BottomNavigationViewModelFactory
import com.anji.babydiary.dailyCheck.DailyCheckViewModel
import com.anji.babydiary.event.EventActivity
import com.anji.babydiary.gnb.main.NavViewModel
import com.anji.babydiary.gnb.main.NavViewModelFactory
import com.anji.babydiary.mainFeed.MainFeedActivity
import com.anji.babydiary.myPage.MyPage
import com.anji.babydiary.shopping.ShoppingActivity
import com.anji.babydiary.tips.TipActivity
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.nav_layout.view.*


abstract class BaseActivity() : AppCompatActivity() {

    lateinit var toolbar:Toolbar;
    lateinit var layout:CollapsingToolbarLayout;
    lateinit var navController:NavController
    lateinit var navViewModel: NavViewModel


    fun setNav(nestedHost:Int):NavViewModel {
        var appBarConfiguration: AppBarConfiguration.Builder
        var navViewClosedHeight:Int = 278
        val navViewModelFactory = NavViewModelFactory(application)
        navViewModel = ViewModelProviders.of(this, navViewModelFactory).get(NavViewModel::class.java)


        navController = this.findNavController(nestedHost)

        if (nestedHost == R.id.eventNestFragment) {
            layout = findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar_event_layout)
            toolbar = findViewById<Toolbar>(R.id.activity_event_toolbar)
        }else {
            layout = findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar_layout)
            toolbar = findViewById<Toolbar>(R.id.activity_toolbar)
            var navView = toolbar.nav_category
            navView.animate().translationY(navView.height.toFloat()).setDuration(1000)
        }


        //toolbar.navigationIcon!!.setVisible(false, false)
        //toolbar.setNavigationIcon(R.drawable.ic_launcher_background)
        setSupportActionBar(toolbar)


        appBarConfiguration = AppBarConfiguration.Builder(navController.graph)
        layout.isTitleEnabled=false

        layout.setupWithNavController(toolbar, navController, appBarConfiguration.build())

        navViewModel.isOpen.observe(this, Observer {
            //layout.nav_category.bringToFront()
            it?.let {
                var layoutParam = layout.layoutParams
                if (!it) {
                    layoutParam.height = 278
                }else {
                    layoutParam.height = 578
                }
            }

        })


        Log.e("navigation icon", "icon: ${toolbar.navigationIcon}")


        return navViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

    }



    fun setBottomNav(idx:Int):BottomNavigationViewModel {
        val bottomNavViewModelFactory = BottomNavigationViewModelFactory(this, idx)
        val bottomNavViewModel = ViewModelProviders.of(this, bottomNavViewModelFactory).get(BottomNavigationViewModel::class.java)
        return bottomNavViewModel
    }


    fun eventIntent() {
        val intent: Intent = Intent(this, EventActivity::class.java)
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        startActivity(intent)
    }

    fun tipIntent() {
        val intent: Intent = Intent(this, TipActivity::class.java)
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        startActivity(intent)
    }
    fun shoppingIntent() {

        val intent: Intent = Intent(this, ShoppingActivity::class.java)
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        startActivity(intent)

    }

    fun mainIntent() {

        val intent: Intent = Intent(this, MainFeedActivity::class.java)
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        startActivity(intent)

    }

    fun myPageIntent() {
        val intent: Intent = Intent(this, MyPage::class.java)
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
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