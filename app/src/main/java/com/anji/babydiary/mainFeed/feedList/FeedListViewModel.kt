package com.anji.babydiary.mainFeed.feedList

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anji.babydiary.R
import com.anji.babydiary.common.CommonCode
import com.anji.babydiary.database.likes.Likes
import com.anji.babydiary.database.likes.LikesDao
import com.anji.babydiary.database.mainFeed.MainFeed
import com.anji.babydiary.database.mainFeed.MainFeedDAO
import com.anji.babydiary.database.profile.ProfileDao
import com.anji.babydiary.database.profile.Profiles
import com.anji.babydiary.gnb.GNBSelect
import kotlinx.coroutines.*
import timber.log.Timber

class FeedListViewModel(
    val mainFeedDAO: MainFeedDAO,
    val profile: ProfileDao,
    val likesDao: LikesDao,
    application: Application
) : AndroidViewModel(application) {

    var allFeeds = mainFeedDAO.selectAll()
    var profileData = MutableLiveData<Profiles>()

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var isCategoryOpen = MutableLiveData<Boolean>()

    init {
        uiScope.launch {
            selectProfile()
        }
        isCategoryOpen.value = false
    }

    suspend fun selectProfile() {
        withContext(Dispatchers.IO) {
            profileData.postValue( profile.selectProfile(CommonCode.USER_IDX) )

        }
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

            /*
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
            */
            count++
        }
    }

    fun selectAll(): LiveData<List<MainFeed>> {
        return mainFeedDAO.selectAll()
    }


    fun onLikeButtonClicked(likeCnt:CharSequence, feedIdx:Long) {
        var like: Likes = Likes()

        like.feed_idx = feedIdx
        like.user_idx = CommonCode.USER_IDX
        like.date = System.currentTimeMillis()

        uiScope.launch {
            likeInsert(like)
        }

        uiScope.launch {
            updateLike(likeCnt, feedIdx)
        }

    }

    private suspend fun likeInsert(like: Likes) {
        withContext(Dispatchers.IO) {
            likesDao.insert(like)
        }
    }


    suspend fun updateLike(likeCnt:CharSequence, idx:Long) {
        withContext(Dispatchers.IO) {
            mainFeedDAO.updateLike(likeCnt.toString().toLong()+1, idx)
        }

    }

    fun onCategorySelectClicked() {
        if (isCategoryOpen.value!! ) {
            isCategoryOpen.value = false
        }else {
            isCategoryOpen.value = true
        }

    }

}


class FeedClickListener(val clickListener:(resultId:Long)->Unit ) {
    fun onClick(result:MainFeed) = clickListener(result.idx)
}
class FeedCommentClickListener(val commentClickListener:(resultId:Long)->Unit) {
    fun onCommentClick(result:MainFeed) = commentClickListener(result.idx)
}




