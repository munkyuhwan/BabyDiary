package com.anji.babydiary.tips.tipsList

import android.app.Application
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
    var dataAll = database.selectAll()

    //var dataWithProfile =database.selectAllWithProfile()

    init {
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
    fun onClick(result: Tips) = clickListener(result.idx)
}







