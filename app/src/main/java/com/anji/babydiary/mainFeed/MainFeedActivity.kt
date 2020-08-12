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

        viewModel.isMain.value = true
        goShopping()

    }

    /*
    private fun setNav() {

        val navViewModelFactory = NavViewModelFactory(application)
        val navViewModel = ViewModelProviders.of(this, navViewModelFactory).get(NavViewModel::class.java)
        binding.navController = navViewModel

        val navController = this.findNavController(R.id.feedNestedHost)
        val layout = findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar_layout)
        val toolbar = findViewById<Toolbar>(R.id.feed_activity_toolbar)

        appBarConfiguration = AppBarConfiguration.Builder(navController.graph)
        layout.isTitleEnabled=false
        layout.setupWithNavController(toolbar, navController, appBarConfiguration.build())

        navViewModel.isOpen.observe(this, Observer {
            binding.mainCategory.bringToFront()
            if (it == true) {
                binding.naviTop.setBackgroundResource(R.drawable.main_appbar_select)
            }else {
                binding.naviTop.setBackgroundResource(R.drawable.main_feed_white)
            }
        })

    }
*/
    fun goShopping() {

        val intent: Intent = Intent(this, TipActivity::class.java)
        startActivity(intent)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.feedNestedHost)
        return navController.navigateUp()
    }

}