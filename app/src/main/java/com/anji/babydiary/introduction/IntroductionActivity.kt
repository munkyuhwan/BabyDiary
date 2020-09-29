package com.anji.babydiary.introduction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.anji.babydiary.R
import com.anji.babydiary.databinding.ActivityIntroductionBinding
import com.anji.babydiary.login.Login

class IntroductionActivity : AppCompatActivity() {


    lateinit var binding:ActivityIntroductionBinding
    lateinit var viewModel: IntroductionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_introduction)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_introduction)

        val application = requireNotNull(this).application
        binding.lifecycleOwner = this

        val viewModelFactory = IntroductionViewModelFactory(application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(IntroductionViewModel::class.java)


        binding.viewModel = viewModel


        binding.toNext.setOnClickListener {
            val intent: Intent = Intent(this, Login::class.java)
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            startActivity(intent)
            finish()
        }


    }
}