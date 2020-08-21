package com.anji.babydiary.myPage

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import com.anji.babydiary.R
import com.anji.babydiary.backgroudnViewModel.BackgroundViewModel
import com.anji.babydiary.backgroudnViewModel.BackgroundViewModelFactory
import com.anji.babydiary.bottomActivity.BottomMenu
import com.anji.babydiary.bottomActivity.resign.Resign
import com.anji.babydiary.common.BaseActivity
import com.anji.babydiary.common.CommonCode
import com.anji.babydiary.databinding.ActivityMyPageBinding
import com.anji.babydiary.gnb.myPage.MyPageNavViewModel
import com.anji.babydiary.gnb.myPage.MyPageNavViewModelFactory
import com.anji.babydiary.myPage.myFeed.MyFeedDirections
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.android.synthetic.main.nav_mypage_layout.view.*

class MyPage : BaseActivity() {

    private lateinit var binding:ActivityMyPageBinding
    private lateinit var layout:CollapsingToolbarLayout
    private lateinit var appBarConfiguration: AppBarConfiguration.Builder

    companion object {
        private lateinit var viewModel: MyPageViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_my_page)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_page)
        binding.lifecycleOwner = this

        val application = requireNotNull(this).application

        val viewModelFactory = MyPageViewModelFactory(application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MyPageViewModel::class.java)

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

                "MyProfile" -> {
                    viewModel.isSub.value = View.VISIBLE
                    viewModel.isMain.value = View.GONE
                    setToolBar(R.id.mypage_write_toolbar, R.id.mypage_write_collapsing_toolbar_layout)
                }
                "MyFamily" -> {
                    viewModel.isSub.value = View.VISIBLE
                    viewModel.isMain.value = View.GONE
                    setToolBar(R.id.mypage_write_toolbar, R.id.mypage_write_collapsing_toolbar_layout)
                }
                "ThemeSetting" -> {
                    viewModel.isSub.value = View.VISIBLE
                    viewModel.isMain.value = View.GONE
                    setToolBar(R.id.mypage_write_toolbar, R.id.mypage_write_collapsing_toolbar_layout)
                }
                "Follower" -> {
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

        if (toolbarId == R.id.mypage_activity_toolbar) {
            toolbar.moreMenuBtn.setOnClickListener {
                val intent: Intent = Intent(this, BottomMenu::class.java)
                intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivityForResult(intent, CommonCode.MYPAGE_ACTIVITY_RESULT)
            }
            toolbar.followerLabel.setOnClickListener {
                navController.navigate(MyFeedDirections.actionMyFeedToFollower("follower"))
            }
            toolbar.num_follower.setOnClickListener {
                navController.navigate(MyFeedDirections.actionMyFeedToFollower("follower"))
            }

            toolbar.followingLabel.setOnClickListener {
                navController.navigate(MyFeedDirections.actionMyFeedToFollower("followee"))
            }
            toolbar.num_following.setOnClickListener {
                navController.navigate(MyFeedDirections.actionMyFeedToFollower("followee"))
            }
        }


        layout.setupWithNavController(toolbar, navController, appBarConfiguration.build())
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


        if (requestCode == CommonCode.MYPAGE_ACTIVITY_RESULT) {

            data?.let {
                it.extras?.let {
                    val itemSelected = it.get("selectedItem")

                    when (itemSelected) {
                        0 -> {
                            navController.navigate(MyFeedDirections.actionMyFeedToMyAlarm())
                        }
                        1 -> {
                            navController.navigate(MyFeedDirections.actionMyFeedToMyProfile())
                        }
                        2 -> {
                            navController.navigate(MyFeedDirections.actionMyFeedToMyFamily())
                        }
                        3 -> {
                            navController.navigate(MyFeedDirections.actionMyFeedToThemeSetting())
                        }
                        4 -> {
                            val intent: Intent = Intent(this, Resign::class.java)
                            startActivity(intent)
                        }
                    }

                }




            }


        }


    }



}