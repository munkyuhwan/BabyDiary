package com.anji.babydiary.tips.tipsList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anji.babydiary.common.CommonCode
import com.anji.babydiary.database.mainFeed.MainFeed
import com.anji.babydiary.database.profile.ProfileDao
import com.anji.babydiary.database.profile.Profiles
import com.anji.babydiary.database.shopping.Tip
import com.anji.babydiary.database.shopping.TipAndProfile
import com.anji.babydiary.database.shopping.TipDao
import com.anji.babydiary.database.shopping.TipDatabase
import kotlinx.coroutines.*

class TipListViewModel(
    var database: TipDao,
    application:Application
) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    var dataAll = MutableLiveData<TipAndProfile>()

    init {
        uiScope.launch {
            getTipAndProfile()
        }
    }

    suspend fun getTipAndProfile() {
        withContext(Dispatchers.IO) {
            database.selectTipWithUser(CommonCode.USER_IDX)
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

}



class TipClickListener(val clickListener:(resultId:Long)->Unit ) {
    fun onClick(result: Tip) = clickListener(result.tip_idx)
}







