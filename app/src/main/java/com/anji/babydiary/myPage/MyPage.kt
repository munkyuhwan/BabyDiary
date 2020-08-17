package com.anji.babydiary.myPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import com.anji.babydiary.R
import com.anji.babydiary.common.BaseActivity
import com.anji.babydiary.databinding.ActivityMyPageBinding
import com.anji.babydiary.gnb.myPage.MyPageNavViewModel
import com.anji.babydiary.gnb.myPage.MyPageNavViewModelFactory
import com.google.android.material.appbar.CollapsingToolbarLayout

class MyPage : BaseActivity() {

    private lateinit var binding:ActivityMyPageBinding
    private lateinit var layout:CollapsingToolbarLayout
    private lateinit var appBarConfiguration: AppBarConfiguration.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_my_page)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_page)
        binding.lifecycleOwner = this

        val application = requireNotNull(this).application

        val viewModelFactory = MyPageViewModelFactory(application)
        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(MyPageViewModel::class.java)

        binding.viewModel = viewModel



        navController = this.findNavController(R.id.myPageNestFragment)


        var actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.back_btn)
        }

        appBarConfiguration = AppBarConfiguration.Builder(navController.graph)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            Log.e("complete","destination change=================================================")

            when(destination.label.toString()) {
                "MyFeed" -> {
                    viewModel.isMain.value = View.VISIBLE
                    viewModel.isSub.value = View.GONE
                    setToolBar(R.id.mypage_activity_toolbar, R.id.mypage_collapsing_toolbar_layout)
                }

                "MyFeedWrite" -> {
                    viewModel.isSub.value = View.VISIBLE
                    viewModel.isMain.value = View.GONE
                    setToolBar(R.id.mypage_write_toolbar, R.id.mypage_write_collapsing_toolbar_layout)
                }


            }
        }




    }

    fun setToolBar(toolbarId:Int, collapsingToolbarLayoutId:Int) {
        layout = findViewById<CollapsingToolbarLayout>(collapsingToolbarLayoutId)
        layout.isTitleEnabled=false


        toolbar = findViewById<Toolbar>(toolbarId)
        setSupportActionBar(toolbar)
        layout.setupWithNavController(toolbar, navController, appBarConfiguration.build())

    }




}