package com.anji.babydiary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.anji.babydiary.database.mainFeed.MainFeed
import com.anji.babydiary.event.EventActivity
import com.anji.babydiary.mainFeed.MainFeedActivity
import com.anji.babydiary.myPage.MyPage
import com.anji.babydiary.shopping.ShoppingActivity
import com.anji.babydiary.tips.TipActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Thread.sleep(1000)
        val intent:Intent = Intent(this, MainFeedActivity::class.java)
        startActivity(intent)
        finish()


    }
}