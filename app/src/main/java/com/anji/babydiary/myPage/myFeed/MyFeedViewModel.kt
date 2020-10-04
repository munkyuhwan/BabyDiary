package com.anji.babydiary.myPage.myFeed

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anji.babydiary.common.MyShare.MyShare
import com.anji.babydiary.database.follow.Follow
import com.anji.babydiary.database.follow.FollowDao
import com.anji.babydiary.database.mainFeed.MainFeed
import com.anji.babydiary.database.mainFeed.MainFeedDAO
import com.anji.babydiary.database.profile.ProfileDao
import com.anji.babydiary.database.profile.Profiles
import kotlinx.coroutines.*

class MyFeedViewModel(val idx:Long,
                      val database:MainFeedDAO,
                      val profileDatabas: ProfileDao,
                      val followDatabase: FollowDao,
                      val userIdx:Long,
                      application: Application) : AndroidViewModel(application) {

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    val myProfile = profileDatabas.selectProfile(idx)
    val selectAll = MutableLiveData<List<MainFeed>>()
    val selectDates = MutableLiveData<List<MainFeed>>()

    val selectAllFollow = followDatabase.selectAll()

    val loggedInIdx = userIdx
    val followIdx = idx.toLong()

    var followeeReusult = MutableLiveData< List<Follow> >()
    var followerResult = MutableLiveData< List<Follow> >()
    var followChecker = MutableLiveData<List<Follow>>()

    var addVisibility = MutableLiveData<Int>()

    var isFollowing = MutableLiveData<Int>()

    init {
        uiScope.launch {
            //selectByIdx()
        }
        uiScope.launch {
            getDates()
        }
        isFollowing.value = View.GONE
        getFollowerChecker()
        getFolloweeResult()
        getFollowerResult()
        if (idx == userIdx) {
            addVisibility.value = View.VISIBLE
        }else {
            addVisibility.value = View.GONE
        }
    }

    fun loadDates() {

        uiScope.launch {
            getDates()
        }
    }

    suspend fun getDates() {
        withContext(Dispatchers.IO) {
            selectDates.postValue( database.selectDates(idx) )
        }
    }

    fun getFolloweeResult() {
        uiScope.launch {
            selectFolloweeResult()
        }
    }
    suspend fun selectFolloweeResult() {
        withContext(Dispatchers.IO) {
            followeeReusult.postValue(followDatabase.selectFollowee(userIdx))
        }
    }

    fun getFollowerResult() {
        uiScope.launch {
            selectFollowerResult()
        }
    }
    suspend fun selectFollowerResult() {
        withContext(Dispatchers.IO) {
            followerResult.postValue(followDatabase.selectFollower(followIdx))
        }
    }

    fun getFollowerChecker() {
        uiScope.launch {
            selectFollowChecker()
        }
    }
    suspend fun selectFollowChecker() {
        withContext(Dispatchers.IO) {
            followChecker.postValue( followDatabase.checkFollow(followIdx, loggedInIdx) )
            Log.e("followchecker","${followChecker.value}")
            if (followChecker.value != null) {
                if (followChecker.value!!.size <= 0) {
                    isFollowing.postValue(View.GONE)
                } else {
                    isFollowing.postValue(View.VISIBLE)
                }
            }else {
                isFollowing.postValue(View.GONE)

            }

        }
    }

    suspend fun selectByIdx() {
        withContext(Dispatchers.IO) {
            selectAll.postValue( database.selectByUserIdx(idx) )
        }
    }


    fun onFollowClicked() {
       if (loggedInIdx != followIdx) {
           Log.e("followcheckerOnfollowclick","${followChecker.value}")
           if (followChecker.value!!.size <= 0) {

                var follow = Follow()
                follow.followee_idx = followIdx
                follow.follower_idx = loggedInIdx

                uiScope.launch {
                    insertFollow(follow)
                }
           }else {
                uiScope.launch {
                    deleteFollow()
                }
           }

        }
    }

    suspend fun deleteFollow() {
        withContext(Dispatchers.IO) {
            followDatabase.deleteAllByFollowe(followIdx, loggedInIdx)
            getFollowerChecker()
            getFolloweeResult()
            getFollowerResult()
        }
    }

    suspend fun insertFollow(follow:Follow) {

        withContext(Dispatchers.IO) {
            followDatabase.insert(follow)
            getFollowerChecker()
            getFolloweeResult()
            getFollowerResult()
        }

    }


}

class MyFeedClickListener(val clickListener:(feedId:Long)->Unit) {
    fun onMyFeedClicked(mainFeed:MainFeed) = clickListener(mainFeed.idx)
}

