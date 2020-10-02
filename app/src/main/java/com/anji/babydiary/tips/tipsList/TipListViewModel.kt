package com.anji.babydiary.tips.tipsList

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.anji.babydiary.common.CommonCode
import com.anji.babydiary.common.MyShare.MyShare
import com.anji.babydiary.database.bookmark.BookMark
import com.anji.babydiary.database.mainFeed.MainFeed
import com.anji.babydiary.database.profile.ProfileDao
import com.anji.babydiary.database.profile.ProfileDatabase
import com.anji.babydiary.database.profile.Profiles
import com.anji.babydiary.database.tip.Tips

import com.anji.babydiary.database.tip.TipsDao
import com.anji.babydiary.database.tip.tipsBookmark.TipBookMark
import com.anji.babydiary.database.tip.tipsBookmark.TipBookMarkDao
import com.anji.babydiary.database.tipLikes.TipLikes
import com.anji.babydiary.database.tipLikes.TipLikesDao
import com.anji.babydiary.database.tipLikes.TipLikesDatabase
import kotlinx.coroutines.*
import okhttp3.Dispatcher

class TipListViewModel(
    var database: TipsDao,
    var bookMarkDatabase: TipBookMarkDao,
    val idx:Long,
    application:Application
) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    //var dataAll = database.selectAll()
    var dataAll = MutableLiveData<List<Tips>>()

    var seletBookMark = MutableLiveData<List<TipBookMark>>()

    var bookMarks = MutableLiveData<List<TipBookMark>>()

    var isCategoryOpen = MutableLiveData<Int>()
    var profileDatabase: ProfileDao
    var tipLikeDatabase: TipLikesDao

    var tipLikeCnt:Long
    var arrowRotate = MutableLiveData<Float>()

    var bookmarkTipIdx:Long = 0

    //var allFeeds = MutableLiveData<List<Tips>>()
    var singleFeed = ArrayList<Tips>()
    // var dataWithProfile =database.selectWithUser()

    init {
        arrowRotate.value = 0f
        tipLikeCnt = 0
        isCategoryOpen.value = View.GONE
        //Log.e("dhife","data: ${dataWithProfile}")
        uiScope.launch {
            selectAll()
        }
        tipLikeDatabase = TipLikesDatabase.getInstance(application).database
        profileDatabase = ProfileDatabase.getInstance(application).database

        uiScope.launch {
            profileSelectAll()
        }

        uiScope.launch {
            getAll()
        }


    }

    suspend fun getAll() {
        withContext(Dispatchers.IO) {
            dataAll.postValue(database.selectWithUser())

        }
    }

    suspend fun profileSelectAll() {
        withContext(Dispatchers.IO) {
            if ( profileDatabase.selectAllTmp().size <= 0 ){
                doInsert(1, "승율아가","승유리를 소개해요","mem_1")
                doInsert(2,"찬호","똘망똘망한 먹보","mem_2")
                doInsert(3, "쥬쥬","포토그래퍼의 보물","mem_3")
                doInsert(4,"오쑥이","우리집 사랑둥이","mem_4")
                doInsert(5,"선우", "착할선 번우","mem_5")
                doInsert(6,"승현아기","이슬부부의 뮤즈","mem_6")
                doInsert(7, "말랑이","작고 소중한 우리의 천사","mem_7")
                doInsert(8,"재재", "세상으로 나오는 날을 기다리는 중","mem_8")
            }
        }
    }





    fun doInsert(i:Int, name:String, intro:String, img:String) {

        var profile = Profiles()
        profile.idx = i.toLong()
        profile.name = name
        profile.id = i
        profile.pass = "${i}"
        profile.introduce = "${intro}"
        profile.imgTmp = img
        uiScope.launch {
            insert(profile)
        }
        //}
    }

    private suspend fun insert(profile: Profiles) {
        withContext(Dispatchers.IO){
            Log.e("dataInsert", "member insert")
            profileDatabase.insert(profile)
        }
    }


    suspend fun selectAll() {
        withContext(Dispatchers.IO) {
            dataAll.postValue(database.selectWithUser())
        }
    }

    fun doSelectByCateogry(sel:Int) {
        arrowRotate.value = 0f
        uiScope.launch {
            if (sel == 99 ) {
                //dataAll = database.selectWithUser()
                selectAll()
            }else {
                selectByCategory(CommonCode.TIP_CATEGORY.get(sel))
            }
        }
    }
    suspend fun selectByCategory(sel:String) {
        withContext(Dispatchers.IO) {
            dataAll.postValue(database.selectByCategory(sel))
        }
    }

    fun onCategorySelectClicked() {
        if (isCategoryOpen.value == View.VISIBLE ) {
            isCategoryOpen.value = View.GONE
            arrowRotate.value = 0f
        }else {
            isCategoryOpen.value = View.VISIBLE
            arrowRotate.value = 180f
        }
    }

    fun insertLike(tipIdx:Long, cnt:Int) {
        var tipLike = TipLikes()
        tipLike.tip_idx = tipIdx
        tipLike.userIdx = MyShare.prefs.getLong("idx", 0)
        uiScope.launch {
            doInsertLike(tipLike)
        }
        uiScope.launch {
            updateLike(cnt)
        }
    }
    suspend fun doInsertLike(tipLike:TipLikes) {
        withContext(Dispatchers.IO) {
            tipLikeDatabase.insert(tipLike)
        }
    }

    suspend fun updateLike(cnt:Int) {
        withContext(Dispatchers.IO) {
            database.updateLike(cnt)
        }
    }

    fun selectSearch(text:String) {
        uiScope.launch {
            querylSearch(text)
        }
    }
    suspend fun querylSearch(text:String) {
        withContext(Dispatchers.IO) {
            dataAll.postValue(database.search(text))
        }
    }

    fun addBookmark(tipIdx:Long) {

        var tipBookmark = TipBookMark()
        tipBookmark.tipIdx = tipIdx
        tipBookmark.userIdx = MyShare.prefs.getLong("idx", 0)

        uiScope.launch {
            queryAddBookmark(tipBookmark)
        }
    }
    fun deleteBookmark(tipIdx: Long) {
        uiScope.launch {
            queryDeleteBookmark(tipIdx)
        }
    }
    suspend fun queryDeleteBookmark(tipIdx: Long) {
        withContext(Dispatchers.IO) {
            bookMarkDatabase.deleteBookmark(MyShare.prefs.getLong("idx", 0), tipIdx)
        }
    }

    fun selectBookmark(tipIdx:Long) {
        bookmarkTipIdx = tipIdx
        uiScope.launch {
            querySelectBookmark(tipIdx)
        }
    }

    suspend fun querySelectBookmark(tipIdx:Long){
        withContext(Dispatchers.IO) {
            seletBookMark.postValue( bookMarkDatabase.selectByTipAndUser(MyShare.prefs.getLong("idx", 0), tipIdx) )
        }
    }

    suspend fun queryAddBookmark(tipBookMark: TipBookMark) {
        withContext(Dispatchers.IO) {
            bookMarkDatabase.insert(tipBookMark)
        }
    }



    fun selectBookmark() {
        uiScope.launch {
            querySelectBookmark()
        }
    }
    suspend fun querySelectBookmark() {
        withContext(Dispatchers.IO) {
            bookMarks.postValue( bookMarkDatabase.selectByUserIdx(MyShare.prefs.getLong("idx", 0)) )
        }
    }


    fun selectBookmarkedFeed(idx:Long) {
        uiScope.launch {
            querySelectBookmarkedFeed(idx)
        }
    }
    suspend fun querySelectBookmarkedFeed(idx:Long) {
        withContext(Dispatchers.IO) {
            singleFeed.add(database.selectSingle(idx))
            dataAll.postValue(singleFeed)
        }
    }

}

class TipClickListener(val clickListener:(resultId:Long)->Unit ) {
    fun onClick(result: Tips) = clickListener(result.idx)
}

class TipLikeClicked(val likeClickListener:(resultId:Long, cnt:CharSequence)->Unit) {
    fun onLikeClick(result:Tips, cnt:CharSequence) = likeClickListener(result.idx, cnt)
}

class TipCommentClicked(val commentClickListener:(resultId:Long)->Unit) {
    fun onCommentClick(result:Tips) = commentClickListener(result.idx)
}

class TipUserClicked(val userClickListener:(resultId:Long)->Unit) {
    fun onUserClick(result:Tips) = userClickListener(result.user_idx)
}

class TipBookMarkClickListener(val bookMarkClickListener:(idx:Long)->Unit) {
    fun onBookMarkClick(result: Tips) = bookMarkClickListener(result.idx)
}




