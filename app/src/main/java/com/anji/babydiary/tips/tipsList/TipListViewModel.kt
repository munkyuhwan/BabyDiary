package com.anji.babydiary.tips.tipsList

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anji.babydiary.common.CommonCode
import com.anji.babydiary.database.mainFeed.MainFeed
import com.anji.babydiary.database.profile.ProfileDao
import com.anji.babydiary.database.profile.Profiles
import com.anji.babydiary.database.shopping.Tips

import com.anji.babydiary.database.shopping.TipsDao
import kotlinx.coroutines.*

class TipListViewModel(
    var database: TipsDao,
    application:Application
) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    var dataAll = MutableLiveData<List<Tips>>()
    var isCategoryOpen = MutableLiveData<Int>()

    //var dataWithProfile =database.selectAllWithProfile()

    init {
        isCategoryOpen.value = View.GONE
        uiScope.launch {
            selectAll()
        }
    }

    suspend fun selectAll() {
        withContext(Dispatchers.IO) {
            dataAll.postValue(database.selectAll())
        }
    }

    fun doSelectByCateogry(sel:Int) {
        uiScope.launch {
            if (sel == 99 ) {
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
    fun onClick(result: Tips) = clickListener(result.idx)
}







