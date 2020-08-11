package com.anji.babydiary.mainFeed.feedList

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anji.babydiary.R
import com.anji.babydiary.database.mainFeed.MainFeed
import com.anji.babydiary.database.mainFeed.MainFeedDAO
import com.anji.babydiary.gnb.GNBSelect
import kotlinx.coroutines.*
import timber.log.Timber

class FeedListViewModel(
    val mainFeedDAO: MainFeedDAO,
    application: Application
) : AndroidViewModel(application) {


    var allFeeds = mainFeedDAO.selectAll()

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        Log.d("query","=================init================================")
        Log.d("query","==========================================================")
    }


     fun insertInto() {
        uiScope.launch {
            insertMainFeed()
        }
    }

    fun clearAll() {
        uiScope.launch {
            deleteAllMainFeeds()
        }
    }
    var count:Int = 0;

    private suspend fun deleteAllMainFeeds() {
        withContext(Dispatchers.IO) {
            mainFeedDAO.deleteAll()
            count = 0;
        }
    }

    private suspend fun insertMainFeed() {
        withContext(Dispatchers.IO) {
            Log.d("query","=================insert================================")
            Log.d("query","==========================================================")

            val mainFeed = MainFeed()
            mainFeed.userId = "tester1"
            mainFeed.title = "test title"
            mainFeed.text = "Textt test "
            mainFeed.likeCnt = 100

            when (count%3) {
                0 -> {
                    mainFeed.imgDir = R.drawable.sample_1
                }
                1 -> {
                    mainFeed.imgDir = R.drawable.sample_2
                }
                2 -> {
                    mainFeed.imgDir = R.drawable.sample_3
                }
            }

            mainFeedDAO.insert(mainFeed)

            count++
        }
    }

    fun selectAll(): LiveData<List<MainFeed>> {
        return mainFeedDAO.selectAll()
    }

}


class FeedClickListener(val clickListener:(resultId:Long)->Unit ) {
    fun onClick(result:MainFeed) = clickListener(result.idx)
}




