package com.anji.babydiary.myPage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.anji.babydiary.R
import com.anji.babydiary.bottomActivity.BottomMenu
import com.anji.babydiary.bottomActivity.resign.Resign
import com.anji.babydiary.common.BaseActivity
import com.anji.babydiary.common.CommonCode
import com.anji.babydiary.common.MyShare.MyShare
import com.anji.babydiary.common.Utils
import com.anji.babydiary.dailyCheck.DailyCheckActivity
import com.anji.babydiary.database.profile.ProfileDatabase
import com.anji.babydiary.database.profile.Profiles
import com.anji.babydiary.databinding.ActivityMyPageBinding
import com.anji.babydiary.login.Login
import com.anji.babydiary.myPage.myFeed.MyFeedDirections
import com.bumptech.glide.Glide
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.android.synthetic.main.nav_mypage_layout.view.*
import java.util.*

class MyPage : BaseActivity() {

    private lateinit var binding:ActivityMyPageBinding
    private lateinit var appBarConfiguration: AppBarConfiguration.Builder

    companion object {
        private lateinit var viewModel: MyPageViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_my_page)

        val idx:Long = MyShare.prefs.getLong("idx", 0L)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_page)
        binding.lifecycleOwner = this

        val application = requireNotNull(this).application
        val database = ProfileDatabase.getInstance(application).database

        val viewModelFactory = MyPageViewModelFactory(database, idx, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MyPageViewModel::class.java)

        binding.viewModel = viewModel

        navController = this.findNavController(R.id.myPageNestFragment)

        val intent = intent.extras

        var intentIdx = 0L
        intent?.let {
            Log.e("intentData","================================================================================")
            Log.e("intentData","${it.getLong("userIdx")}")
            Log.e("intentData","================================================================================")
            intentIdx = it.getLong("userIdx")
            //idx = it.getLong("userIdx")
        }

        if (intentIdx == 0L) {
            binding.bottomNav = setBottomNav(4)
        }else {
            if (intentIdx == idx) {
                binding.bottomNav = setBottomNav(4)

            }else {
                binding.bottomNav = setBottomNav(2)

            }
        }

        binding.fab.setOnClickListener {
            val intent: Intent = Intent(this, DailyCheckActivity::class.java)
            startActivity(intent)
        }

        viewModel.selectAll.observe(this, androidx.lifecycle.Observer {

            Log.e("member","==================================================================")
            Log.e("member","${it}")
            Log.e("member","==================================================================")

        })

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
                        5 -> {
                            Log.e("tag","activity result 5================================================")
                            val intent: Intent = Intent(this, Login::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK and  Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            this.finish()
                        }
                    }

                }




            }


        }


    }



}