package com.anji.babydiary

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import com.anji.babydiary.common.BaseActivity
import com.anji.babydiary.common.CommonCode
import com.anji.babydiary.database.profile.ProfileDao
import com.anji.babydiary.database.profile.ProfileDatabase
import com.anji.babydiary.database.profile.Profiles
import com.anji.babydiary.databinding.ActivityMainBinding
import com.anji.babydiary.login.Login
import com.anji.babydiary.mainFeed.MainFeedActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.coroutines.*
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException
import java.lang.Runnable
import java.util.*
import kotlin.system.exitProcess

class MainActivity : BaseActivity() {
    //var job = Job()
    //var uiScope = CoroutineScope(Dispatchers.Main + job)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)


        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        //month -1
        /*
        val expDate = GregorianCalendar(2020, 8, 9) // midnight
        val now = GregorianCalendar()

        val isExpired = now.after(expDate)

        if (isExpired) {
            Toast.makeText(this, "사용기간이 만료되었습니다.", Toast.LENGTH_SHORT).show()
            finishAffinity()
            finish()
            exitProcess(-1)
        }
        */
        //userData = database.checkUserData()
        initUserData()
        userData?.observe(this, androidx.lifecycle.Observer {


            if (it.size <= 0) {
                initializeData()
            }

            if (it.size == 8 ) {
                //feedDataInitialize()
                feedDataCheck()
            }

        })
        feedData?.observe(this, androidx.lifecycle.Observer {
            Log.e("feedDataValue","======================================================")
            Log.e("feedDataValue","${it}")
            Log.e("feedDataValue","======================================================")
            if (it.size == 7) {
                uiScope.launch {
                     delay()
                }
            }
            else if (it.size == 0) {
                feedDataInitialize()
            }
        })
    }
    suspend fun delay() {
        withContext(Dispatchers.IO) {
            Thread.sleep(3000)
            goMain()
        }
    }

    private fun goMain() {
        //CommonCode.USER_IDX = 1
        val intent:Intent = Intent(this, Login::class.java)
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        startActivity(intent)
        finish()
    }



}