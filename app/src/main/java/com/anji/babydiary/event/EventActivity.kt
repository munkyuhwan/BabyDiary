package com.anji.babydiary.event

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.anji.babydiary.R
import com.anji.babydiary.common.BaseActivity
import com.anji.babydiary.databinding.ActivityEventBinding

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

        setNav(R.id.eventNestFragment)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->

            Log.e("nav","============================================")
            Log.e("nav","${destination.label}")

            when(destination.label.toString()) {
                "EventList" ->
                    toolbar.setBackgroundResource(R.drawable.actionbar_transparent)
                "EventDetail" ->
                    toolbar.setBackgroundResource(R.drawable.actionbar_event)

            }

            Log.e("nav","============================================")

        }

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

}