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
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var job = Job()
        var uiScope = CoroutineScope(Dispatchers.Main + job)

        uiScope.launch {
            delay()
        }

    }

    suspend fun delay() {
        withContext(Dispatchers.IO) {

            Thread.sleep(2000)
            goMain()
        }
    }

    private fun goMain() {
        val intent:Intent = Intent(this, MainFeedActivity::class.java)
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        startActivity(intent)
        finish()
    }
}