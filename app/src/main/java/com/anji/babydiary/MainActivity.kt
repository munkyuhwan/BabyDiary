package com.anji.babydiary

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.anji.babydiary.common.CommonCode
import com.anji.babydiary.databinding.ActivityMainBinding
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

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        //month -1
        val expDate = GregorianCalendar(2020, 8, 9) // midnight
        val now = GregorianCalendar()

        val isExpired = now.after(expDate)

        if (isExpired) {
            Toast.makeText(this, "사용기간이 만료되었습니다.", Toast.LENGTH_SHORT).show()
            finishAffinity()
            finish()
            exitProcess(-1)
        }

        var job = Job()
        var uiScope = CoroutineScope(Dispatchers.Main + job)

        uiScope.launch {
         //   delay()
        }

        binding.goMain.setOnClickListener {
            if (binding.userIdx.text != null) {
                CommonCode.USER_IDX = binding.userIdx.text.toString().toLong()
                goMain()
            }
        }


        //getAppKeyHash();
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("TAG", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }
                // Get new Instance ID token
                val token = task.result!!.token
                Log.e("tag", token)


                /*
                val client = OkHttpClient()
                val body: RequestBody = FormBody.Builder()
                    .add("Token", token)
                    .build()

                //request
                val request = Request.Builder()
                    .url("http://gba2020.cafe24.com/board/join_apush.php")
                    .post(body)
                    .build()
                Thread(Runnable {
                    try {
                        client.newCall(request).execute()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }).start()

                 */

                //FirebaseMessaging.getInstance().subscribeToTopic("fcm_and");
            })


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