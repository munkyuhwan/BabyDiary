package com.anji.babydiary.search

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.anji.babydiary.R
import com.anji.babydiary.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_search)


        val binding = DataBindingUtil.setContentView<ActivitySearchBinding>(this, R.layout.activity_search)

        binding.searchBtn.setOnClickListener {
            if (binding.searchInputText.text.toString().equals("")) {
                Toast.makeText(this, "텍스트를 입력 해 주세요.",Toast.LENGTH_SHORT).show()
            }else {
                finishWithResult(binding.searchInputText.text.toString())
            }
        }

        binding.dimLayer.setOnClickListener {
            finish()
        }

    }

    private fun finishWithResult(searchKeyword:String) {

        var intentResult: Intent = Intent()
        intentResult.putExtra("keyword", searchKeyword)
        setResult(Activity.RESULT_OK, intentResult)
        finish()

    }



}