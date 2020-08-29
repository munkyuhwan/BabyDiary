package com.anji.babydiary.myPage.myFeedWrite

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.text.style.LineHeightSpan
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anji.babydiary.common.CommonCode
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


    init {
        isShown.value = View.GONE
        selectedAddress.value = SelectedAddress.address
        isDone.value = false
    }

    fun onImageSelected(data: Intent?) {
        selectedImage.value = data?.data!!
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
        mainFeed.userIdx = CommonCode.USER_IDX


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





