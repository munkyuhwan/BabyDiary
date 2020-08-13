package com.anji.babydiary.common

import android.content.DialogInterface
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.anji.babydiary.R
import com.anji.babydiary.generated.callback.OnClickListener
import com.anji.babydiary.gnb.NavViewModel
import com.anji.babydiary.gnb.NavViewModelFactory
import com.google.android.material.appbar.CollapsingToolbarLayout

abstract class BaseActivity() : AppCompatActivity() {

    lateinit var toolbar:Toolbar;
    lateinit var navController:NavController

    fun setNav(nestedHost:Int) {
        var appBarConfiguration: AppBarConfiguration.Builder

        val navViewModelFactory = NavViewModelFactory(application)
        val navViewModel = ViewModelProviders.of(this, navViewModelFactory).get(NavViewModel::class.java)
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


    fun bindGNB() {

    }




}