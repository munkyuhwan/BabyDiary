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
import com.anji.babydiary.databinding.ActivityMyPageBinding
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

abstract class BaseActivity() : AppCompatActivity() {

    lateinit var toolbar:Toolbar;
    lateinit var navController:NavController
    lateinit var navViewModel: NavViewModel

    fun setNav(nestedHost:Int) {
        var appBarConfiguration: AppBarConfiguration.Builder

        val navViewModelFactory = NavViewModelFactory(application)
        navViewModel = ViewModelProviders.of(this, navViewModelFactory).get(NavViewModel::class.java)

        //binding.navController = navViewModel

        navController = this.findNavController(nestedHost)
        val layout = findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar_layout)
        toolbar = findViewById<Toolbar>(R.id.activity_toolbar)



        setSupportActionBar(toolbar)
        var actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.back_btn)
        }

        appBarConfiguration = AppBarConfiguration.Builder(navController.graph)
        layout.isTitleEnabled=false
        layout.setupWithNavController(toolbar, navController, appBarConfiguration.build())


        navViewModel.isOpen.observe(this, Observer {
            //layout.nav_category.bringToFront()
        })



    }

    fun goTip() {
        val intent: Intent = Intent(this, TipActivity::class.java)
        startActivity(intent)

    }
    fun goShopping() {

        val intent: Intent = Intent(this, ShoppingActivity::class.java)
        startActivity(intent)

    }

    fun goMain() {

        val intent: Intent = Intent(this, MainFeedActivity::class.java)
        startActivity(intent)

    }

    fun goEvent() {

        val intent: Intent = Intent(this, EventActivity::class.java)
        startActivity(intent)

    }

    fun goMyPage() {

        val intent: Intent = Intent(this, MyPage::class.java)
        startActivity(intent)

    }
    fun myPageNav(nestedHost:Int, binding: ActivityMyPageBinding) {


    }

    fun bindGNB() {

    }




}