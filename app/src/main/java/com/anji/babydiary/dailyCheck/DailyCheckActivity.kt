package com.anji.babydiary.dailyCheck

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.anji.babydiary.R
import com.anji.babydiary.common.BaseActivity

class DailyCheckActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_check)

        findNavController(R.id.dailyCheckNestFragment)

        //setNav(R.id.dailyCheckNestFragment)
        //val navViewModelFactory = NavViewModelFactory(application)
        //navController = this.findNavController(R.id.dailyCheckNestFragment)

    }
}