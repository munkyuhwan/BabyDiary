package com.anji.babydiary.tips.tipsList

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.anji.babydiary.common.CommonCode
import com.anji.babydiary.common.MyShare.MyShare
import com.anji.babydiary.database.profile.ProfileDao
import com.anji.babydiary.database.profile.ProfileDatabase
import com.anji.babydiary.database.profile.Profiles
import com.anji.babydiary.database.tip.TipWithUser
import com.anji.babydiary.database.tip.Tips

import com.anji.babydiary.database.tip.TipsDao
import com.anji.babydiary.database.tipLikes.TipLikes
import com.anji.babydiary.database.tipLikes.TipLikesDao
import com.anji.babydiary.database.tipLikes.TipLikesDatabase
import kotlinx.coroutines.*
import okhttp3.Dispatcher

class TipListViewModel(
    var database: TipsDao,
    val idx:Long,
    application:Application
) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    //var dataAll = database.selectAll()
    var dataAll = database.selectWithUser()



    var isCategoryOpen = MutableLiveData<Int>()
    var profileDatabase: ProfileDao
    var tipLikeDatabase: TipLikesDao

    var tipLikeCnt:Long

   // var dataWithProfile =database.selectWithUser()

    init {
        tipLikeCnt = 0
        isCategoryOpen.value = View.GONE
        //Log.e("dhife","data: ${dataWithProfile}")
        uiScope.launch {
            selectAll()
        }
        tipLikeDatabase = TipLikesDatabase.getInstance(application).database
        profileDatabase = ProfileDatabase.getInstance(application).database
        val ddd = profileDatabase.selectAllTmp()

        if (ddd.size <= 0) {
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
            //dataAll.postValue( database.selectWithUser())
        }
    }

    fun doSelectByCateogry(sel:Int) {
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
            dataAll = database.selectByCategory(sel)
        }
    }

    fun onCategorySelectClicked() {
        if (isCategoryOpen.value == View.VISIBLE ) {
            isCategoryOpen.value = View.GONE
        }else {
            isCategoryOpen.value = View.VISIBLE
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

}

class TipClickListener(val clickListener:(resultId:Long)->Unit ) {
    fun onClick(result: TipWithUser) = clickListener(result.tips!!.idx)
}

class TipLikeClicked(val likeClickListener:(resultId:Long, cnt:CharSequence)->Unit) {
    fun onLikeClick(result:TipWithUser, cnt:CharSequence) = likeClickListener(result.tips!!.idx, cnt)
}

class TipCommentClicked(val commentClickListener:(resultId:Long)->Unit) {
    fun onCommentClick(result:TipWithUser) = commentClickListener(result.tips!!.idx)
}






