package com.anji.babydiary.event

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.anji.babydiary.R
import com.anji.babydiary.common.BaseActivity
import com.anji.babydiary.dailyCheck.DailyCheckActivity
import com.anji.babydiary.databinding.ActivityEventBinding
import java.util.*

class EventActivity : BaseActivity() {


    private lateinit var binding:ActivityEventBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_event)

        binding = DataBindingUtil.setContentView<ActivityEventBinding>(this, R.layout.activity_event)

        val application = requireNotNull(this).application

        val viewModelFactory = EventViewModelFactory(application)
        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(EventViewModel::class.java)
        binding.eventViewModel = viewModel

        /*
        setNav(R.id.eventNestFragment)
        navController = this.findNavController(R.id.eventNestFragment)
        binding.navController = navViewModel

        navController.addOnDestinationChangedListener { controller, destination, arguments ->

            when(destination.label.toString()) {
                "EventList" ->
                    toolbar.setBackgroundResource(R.drawable.actionbar_transparent)
                "EventDetail" ->
                    toolbar.setBackgroundResource(R.drawable.actionbar_event)

            }

        }

         */

        //setOnclickMenu()

        binding.bottomNav = setBottomNav(0)

        binding.fab.setOnClickListener {
            val intent: Intent = Intent(this, DailyCheckActivity::class.java)
            startActivity(intent)
        }

    }




}