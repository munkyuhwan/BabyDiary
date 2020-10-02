package com.anji.babydiary.addBaby

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.anji.babydiary.R
import com.anji.babydiary.databinding.ActivityAddBabyBinding

class AddBaby : AppCompatActivity() {


    lateinit var binding:ActivityAddBabyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_baby)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_baby)
        binding.lifecycleOwner = this

        binding.menuLayer.setOnClickListener {

            Toast.makeText(this, "준비중입니다.",Toast.LENGTH_SHORT).show()
            finish()
        }

    }


}