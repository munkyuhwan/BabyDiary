package com.anji.babydiary.mainFeed.feedList

import android.app.Activity
import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anji.babydiary.common.MyShare.MyShare
import com.anji.babydiary.database.bookmark.BookMark
import com.anji.babydiary.database.bookmark.BookMarkDao
import com.anji.babydiary.database.likes.Likes
import com.anji.babydiary.database.likes.LikesDao
import com.anji.babydiary.database.mainFeed.MainFeed
import com.anji.babydiary.database.mainFeed.MainFeedDAO
import com.anji.babydiary.database.profile.ProfileDao
import kotlinx.coroutines.*
import okhttp3.Dispatcher

class FeedListViewModel(
    val mainFeedDAO: MainFeedDAO,
    val profile: ProfileDao,
    val likesDao: LikesDao,
    val bookMarkDao: BookMarkDao,
    val activity: Activity,
    application: Application
) : AndroidViewModel(application) {

    var selectedItem = MutableLiveData<String>()

    val typeArea:String = "area"
    val typeAge:String = "age"

    var allFeeds = MutableLiveData<List<MainFeed>>()
    var singleFeed = ArrayList<MainFeed>()
    var arrowRotation = MutableLiveData<Float>()

    var profileData = profile.selectProfile(MyShare.prefs.getLong("idx", 0L))


    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var isCategoryOpen = MutableLiveData<Int>()
    //var feedWithUser = mainFeedDAO.selectWithProfile()

    var bookMarks = MutableLiveData<List<BookMark>>()

    //var feedWithUser = mainFeedDAO.getFeedWithUser()
    init {
        isCategoryOpen.value = View.VISIBLE
        allFeeds()
        arrowRotation.value = 0F
    }

    fun allFeeds() {
        uiScope.launch {
            getAllfeed()
        }
    }

    suspend fun getAllfeed() {
        withContext(Dispatchers.IO) {
            allFeeds.postValue( mainFeedDAO.selectAllMutable() )
            arrowRotation.postValue(0F)
            isCategoryOpen.postValue(View.GONE)
            selectedItem.postValue("전체보기")
        }
    }

    fun onTypeClick(type:String) {
        Log.e("click","categoryclicked")
        uiScope.launch {
            selectByType(type)
        }
    }

    suspend fun selectByType(type:String) {
        withContext(Dispatchers.IO) {
          //  allFeeds = mainFeedDAO.selectAllByType(type)
              allFeeds.postValue( mainFeedDAO.selectAllByTypeMutable(type) )
            arrowRotation.postValue(0F)
            isCategoryOpen.postValue(View.GONE)
            if (type.equals("age")) {
                selectedItem.postValue("비슷한 연령")
            }else {
                selectedItem.postValue("내 주변")
            }
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
        like.user_idx = MyShare.prefs.getLong("idx", 0L)
        like.date = System.currentTimeMillis()

        uiScope.launch {
            likeInsert(like)
        }

        uiScope.launch {
            updateLike(likeCnt, feedIdx)
        }

        allFeeds()

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
        if (isCategoryOpen.value == View.GONE ) {
            isCategoryOpen.value = View.VISIBLE
        }else {
            isCategoryOpen.value = View.GONE
        }
        arrowRotation.value = 180F

    }

    fun selectByKeyword(keyword:String) {
        Log.e("keyword","${keyword}")
        uiScope.launch {
            queryByKeyword(keyword)
        }
    }
    suspend fun queryByKeyword(keyword:String) {
        withContext(Dispatchers.IO) {
            allFeeds.postValue(mainFeedDAO.selectAllByKeyword(keyword))
        }
    }

    fun insertBookMark(idx:Long) {
        var bookMark = BookMark()
        bookMark.user_idx = MyShare.prefs.getLong("idx",0)
        bookMark.feed_idx = idx

        uiScope.launch {
            queryBookMark(bookMark)
        }
    }

    suspend fun queryBookMark(bookMark: BookMark) {
        withContext(Dispatchers.IO) {
            bookMarkDao.insert(bookMark)
        }
    }

    fun selectBookmark() {
        uiScope.launch {
            querySelectBookmark()
        }
    }
    suspend fun querySelectBookmark() {
        withContext(Dispatchers.IO) {
            bookMarks.postValue( bookMarkDao.selectAllByUserIdx(MyShare.prefs.getLong("idx", 0)) )
        }
    }

    fun selectBookmarkedFeed(idx:Long) {
        uiScope.launch {
            querySelectBookmarkedFeed(idx)
        }
    }
    suspend fun querySelectBookmarkedFeed(idx:Long) {
        withContext(Dispatchers.IO) {
            singleFeed.add(mainFeedDAO.selectSingleMut(idx))
            allFeeds.postValue(singleFeed)
        }
    }

}


class FeedClickListener(val clickListener:(resultId:Long)->Unit ) {
    fun onClick(result:MainFeed) = clickListener(result.idx)
}

class MemberClickListener(val clickListener:(resultId:Long)->Unit ) {
    fun onClick(result:MainFeed) = clickListener(result.userIdx)
}

class FeedCommentClickListener(val commentClickListener:(resultId:Long)->Unit) {
    fun onCommentClick(result:MainFeed) = commentClickListener(result.idx)
}

class BookMarkClickListener(val bookMarkClickListener:(idx:Long)->Unit) {
    fun onBookMarkClick(result:MainFeed) = bookMarkClickListener(result.idx)
}




