package com.anji.babydiary.tips.tipsList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.anji.babydiary.database.mainFeed.MainFeed
import com.anji.babydiary.database.shopping.Tip
import com.anji.babydiary.database.shopping.TipDao
import com.anji.babydiary.database.shopping.TipDatabase
import kotlinx.coroutines.*

class TipListViewModel(
    var database: TipDao,
    application:Application
) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    var dataAll = database.selectAll()

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
    fun onClick(result: Tip) = clickListener(result.idx)
}







