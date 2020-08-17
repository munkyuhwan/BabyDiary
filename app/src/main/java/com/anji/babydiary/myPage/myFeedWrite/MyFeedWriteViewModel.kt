package com.anji.babydiary.myPage.myFeedWrite

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anji.babydiary.database.mainFeed.MainFeedDAO

class MyFeedWriteViewModel(val database:MainFeedDAO, application: Application) : AndroidViewModel(application) {

    var isShown = MutableLiveData<Int>()

    init {
        isShown.value = View.GONE
    }

}




