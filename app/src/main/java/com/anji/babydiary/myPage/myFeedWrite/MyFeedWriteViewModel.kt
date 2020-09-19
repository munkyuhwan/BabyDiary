package com.anji.babydiary.myPage.myFeedWrite

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.text.style.LineHeightSpan
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anji.babydiary.common.CommonCode
import com.anji.babydiary.common.MyShare.MyShare
import com.anji.babydiary.common.Utils
import com.anji.babydiary.database.mainFeed.MainFeed
import com.anji.babydiary.database.mainFeed.MainFeedDAO
import com.anji.babydiary.myPage.myFeedWriteLocation.SelectedAddress
import kotlinx.coroutines.*

class MyFeedWriteViewModel(val database:MainFeedDAO, application: Application) : AndroidViewModel(application) {

    var isShown = MutableLiveData<Int>()
    var selectedAddress = MutableLiveData<String>()
    var selectedImage = MutableLiveData<Uri>()

    var isDone = MutableLiveData<Boolean>()

    var job = Job()
    var uiScope = CoroutineScope(Dispatchers.Main + job)

    var textColor = arrayOf(
        "BLACK",
        "BLUE",
        "RED",
        "YELLOW",
        "WHITE",
        "GREEN"
    )

    var textSizes = arrayOf(
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "10",
        "13",
        "15",
        "16",
        "17",
        "20",
        "30",
        "40",
        "50"
    )

    init {
        isShown.value = View.GONE
        selectedAddress.value = SelectedAddress.address
        isDone.value = false
    }

    fun onImageSelected(data: Intent?) {
        selectedImage.value = data?.data!!
        Log.e("selectedImg","====img:${selectedImage.value}===")
    }

    fun complete(title:String, height:String, weight:String, head:String, location:String, toSpouser:String ) {
        var mainFeed = MainFeed()

        mainFeed.title = title
        mainFeed.height = height.toLong()
        mainFeed.weight = weight.toLong()
        mainFeed.head = head.toLong()
        mainFeed.location = location
        mainFeed.toSpouser = toSpouser
        mainFeed.imgDir = selectedImage.value.toString()
        mainFeed.userIdx = MyShare.prefs.getLong("idx", 0L)

        val year = Utils.getDate(System.currentTimeMillis(), "YYYY")
        val month = Utils.getDate(System.currentTimeMillis(), "M")
        val date = Utils.getDate(System.currentTimeMillis(), "d")

        Log.e("date","================================================================")
        Log.e("date","${year} ${month} ${date}")
        Log.e("date","================================================================")


        mainFeed.year = year!!.toInt()
        mainFeed.month = month!!.toInt()
        mainFeed.date = date!!.toInt()
        mainFeed.timeMilli = System.currentTimeMillis()


        uiScope.launch {
            insert(mainFeed)
            isDone.value = true
        }


    }

    suspend fun insert(mainFeed:MainFeed) {
        withContext(Dispatchers.IO) {
            database.insert(mainFeed)
        }
    }

}





