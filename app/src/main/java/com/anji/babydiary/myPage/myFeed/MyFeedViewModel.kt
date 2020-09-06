package com.anji.babydiary.myPage.myFeed

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anji.babydiary.common.CommonCode
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
                      application: Application) : AndroidViewModel(application) {

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    val myProfile = MutableLiveData<Profiles>()
    val selectAll = MutableLiveData<List<MainFeed>>()

    val loggedInIdx = CommonCode.USER_IDX.toLong()
    val followIdx = idx.toLong()

    var followeeReusult = followDatabase.selectFollowee(loggedInIdx)
    var followerResult = followDatabase.selectFollower(loggedInIdx)
    var followChecker = followDatabase.checkFollow(loggedInIdx, followIdx)

    init {
        uiScope.launch {
            selectByIdx()
        }

        uiScope.launch {
            selectAll()
        }

    }

    suspend fun selectByIdx() {
        withContext(Dispatchers.IO) {
            selectAll.postValue( database.selectByUserIdx(idx) )
        }
    }

    suspend fun selectAll() {
        withContext(Dispatchers.IO) {
            myProfile.postValue(profileDatabas.selectProfile(idx))
        }
    }

    fun onFollowClicked() {

        Log.e("folow","${loggedInIdx} / ${followIdx}")
        if (loggedInIdx != followIdx) {
            Log.e("folow","===========================================")
            Log.e("folow","${followChecker.value!!.size}")

            if (followChecker.value!!.size <= 0) {
                var follow = Follow()
                follow.followee_idx = followIdx
                follow.follower_idx = loggedInIdx

                Log.e("folow","===========================================")
                Log.e("folow","insert!!!!!!")
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
        }
    }

    suspend fun insertFollow(follow:Follow) {

        Log.e("folow","===========================================")
        Log.e("folow","do insert!!!!!!")
        withContext(Dispatchers.IO) {
            Log.e("folow","===========================================")
            Log.e("folow","do insert with context!!!!!!")
            followDatabase.insert(follow)
        }

    }


}

class MyFeedClickListener(val clickListener:(feedId:Long)->Unit) {
    fun onMyFeedClicked(mainFeed:MainFeed) = clickListener(mainFeed.idx)
}

