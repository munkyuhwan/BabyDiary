package com.anji.babydiary.shopping

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.navigation.ui.AppBarConfiguration
import com.anji.babydiary.common.BaseActivity
import com.anji.babydiary.R
import com.anji.babydiary.databinding.ActivityShoppingBinding

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
        setOnclickMenu()
    }

    fun setOnclickMenu() {

        binding.tabPopBtn.setOnClickListener {
            goEvent()
        }
        binding.tabShopBtn.setOnClickListener {
            goShopping()
        }
        binding.tabMainBtn.setOnClickListener {
            goMain()
        }
        binding.tabTipBtn.setOnClickListener {
            goTip()
        }
        binding.tabMyPageBtn.setOnClickListener {
            goMyPage()
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


    }

/*
     fun setNav() {

        val navViewModelFactory = NavViewModelFactory(application)
        val navViewModel = ViewModelProviders.of(this, navViewModelFactory).get(NavViewModel::class.java)
        binding.navController = navViewModel

        val navController = this.findNavController(R.id.shoppingNestedHost)
        val layout = findViewById<CollapsingToolbarLayout>(R.id.shopping_toolbar_layout)
        val toolbar = findViewById<Toolbar>(R.id.shopping_activity_toolbar)

        appBarConfiguration = AppBarConfiguration.Builder(navController.graph)
        layout.isTitleEnabled=false
        layout.setupWithNavController(toolbar, navController, appBarConfiguration.build())

        navViewModel.isOpen.observe(this, Observer {
            Log.e("menu clicked","======================================================")
            Log.e("menu clicked","======================================================")
            Log.e("menu clicked","======================================================")
            if (it == true) {
                binding.naviTop.setBackgroundResource(R.drawable.main_appbar_select)
            }else {
                binding.naviTop.setBackgroundResource(R.drawable.main_feed_white)
            }
            binding.shoppingCategory.bringToFront()
        })

    }
*/
}