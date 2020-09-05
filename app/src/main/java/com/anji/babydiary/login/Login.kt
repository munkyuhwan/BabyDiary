package com.anji.babydiary.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.anji.babydiary.R
import com.anji.babydiary.database.profile.ProfileDatabase
import com.anji.babydiary.databinding.ActivityLoginBinding
import com.anji.babydiary.mainFeed.MainFeedActivity

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

        val binding = DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)

        val application = getApplication()
        val database = ProfileDatabase.getInstance(applicationContext).database

        val viewModelFactory = LoginViewModelFactory(database, application)
        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)


        binding.viewModel = viewModel

        viewModel.idx.observe(this, Observer {
            it?.let {
                if (it != 0L) {
                    val intent = Intent(this, MainFeedActivity::class.java)
                    startActivity(intent)
                    //finish()
                }
            }
        })


    }
}