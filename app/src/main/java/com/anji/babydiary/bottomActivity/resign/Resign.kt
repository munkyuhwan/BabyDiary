package com.anji.babydiary.bottomActivity.resign

import android.database.DatabaseUtils
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.anji.babydiary.R
import com.anji.babydiary.databinding.ActivityResignBinding

class Resign : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_resign)

        val binding = DataBindingUtil.setContentView<ActivityResignBinding>(this, R.layout.activity_resign)

        binding.doResign.setOnClickListener {

        }

        binding.doCancel.setOnClickListener {
            finish()
        }

    }
}