package com.anji.babydiary.mainFeed

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.anji.babydiary.common.BaseActivity
import com.anji.babydiary.R
import com.anji.babydiary.databinding.ActivityMainFeedBinding
import com.anji.babydiary.shopping.ShoppingActivity
import com.anji.babydiary.tips.TipActivity

class MainFeedActivity() : BaseActivity() {
    private lateinit var binding:ActivityMainFeedBinding
    private lateinit var appBarConfiguration: AppBarConfiguration.Builder

    private lateinit var viewModel: MainFeedViewModel
    private lateinit var viewModelFactory: MainFeedViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main_feed)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_feed)

        val application = requireNotNull(this).application

        viewModelFactory = MainFeedViewModelFactory(application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainFeedViewModel::class.java)

        binding.lifecycleOwner = this
        binding.feedMain = viewModel


        setNav(R.id.feedNestedHost)

        navViewModel.isMain.value = true

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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.feedNestedHost)
        return navController.navigateUp()
    }

}