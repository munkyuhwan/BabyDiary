package com.anji.babydiary.myPage.myFeed

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anji.babydiary.common.CommonCode
import com.anji.babydiary.database.mainFeed.MainFeed
import com.anji.babydiary.database.mainFeed.MainFeedDAO
import kotlinx.coroutines.*

class MyFeedViewModel(val database:MainFeedDAO, application: Application) : AndroidViewModel(application) {


    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    val selectAll = MutableLiveData<List<MainFeed>>()
    init {
        uiScope.launch {
            selectByIdx()
        }
    }

    suspend fun selectByIdx() {
        withContext(Dispatchers.IO) {
            selectAll.postValue( database.selectByUserIdx(CommonCode.USER_IDX) )
        }
    }

}

class MyFeedClickListener(val clickListener:(feedId:Long)->Unit) {
    fun onMyFeedClicked(mainFeed:MainFeed) = clickListener(mainFeed.idx)
}

