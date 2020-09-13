package com.anji.babydiary.tips.tipsList

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.anji.babydiary.common.CommonCode
import com.anji.babydiary.database.tip.TipWithUser
import com.anji.babydiary.database.tip.Tips

import com.anji.babydiary.database.tip.TipsDao
import kotlinx.coroutines.*

class TipListViewModel(
    var database: TipsDao,
    val idx:Long,
    application:Application
) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    //var dataAll = database.selectAll()
    var dataAll = MutableLiveData<List<TipWithUser>>()
    var isCategoryOpen = MutableLiveData<Int>()

   // var dataWithProfile =database.selectWithUser()

    init {
        isCategoryOpen.value = View.GONE
        //Log.e("dhife","data: ${dataWithProfile}")
        uiScope.launch {
            selectAll()
        }
    }

    suspend fun selectAll() {
        withContext(Dispatchers.IO) {
            dataAll.postValue( database.selectWithUser())
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
            dataAll.postValue(database.selectByCategory(sel))
        }
    }

    fun clearAll() {
        uiScope.launch {
            deleteAll()
        }
    }

    private suspend fun deleteAll() {
        withContext(Dispatchers.IO) {
            database.deleteAll()
        }
    }
    fun onCategorySelectClicked() {
        if (isCategoryOpen.value == View.VISIBLE ) {
            isCategoryOpen.value = View.GONE
        }else {
            isCategoryOpen.value = View.VISIBLE
        }

    }

}

class TipClickListener(val clickListener:(resultId:Long)->Unit ) {
    fun onClick(result: TipWithUser) = clickListener(result.tips!!.idx)
}

class TipLikeClicked(val likeClickListener:(resultId:Long)->Unit) {
    fun onLikeClick(result:TipWithUser) = likeClickListener(result.tips!!.idx)
}

class TipCommentClicked(val commentClickListener:(resultId:Long)->Unit) {
    fun onCommentClick(result:TipWithUser) = commentClickListener(result.tips!!.idx)
}






