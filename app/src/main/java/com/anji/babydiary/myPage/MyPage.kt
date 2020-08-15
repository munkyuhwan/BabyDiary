package com.anji.babydiary.myPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.anji.babydiary.R
import com.anji.babydiary.common.BaseActivity
import com.anji.babydiary.databinding.ActivityMyPageBinding

class MyPage : BaseActivity() {

    private lateinit var binding:ActivityMyPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_my_page)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_page)



        //setNav(R.id.myPageNestFragment)



    }
}