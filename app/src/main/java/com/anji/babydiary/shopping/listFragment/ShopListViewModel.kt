package com.anji.babydiary.shopping.listFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.anji.babydiary.R
import com.anji.babydiary.database.mainFeed.MainFeed
import com.anji.babydiary.database.shopping.Shopping
import com.anji.babydiary.database.shopping.ShoppingDao
import com.anji.babydiary.database.shopping.ShoppingDatabase
import kotlinx.coroutines.*

class ShopListViewModel(
    val database:ShoppingDao,
    application: Application
    ) : AndroidViewModel(application) {


    var allProduct:LiveData<List<Shopping>> = database.selectAll()

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {

    }

    var count:Int = 0;

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


    fun selectAll(): LiveData<List<Shopping>> {
        return database.selectAll()
    }

}

class ProductClickListener(val clickListener:(resultId:Long)->Unit ) {
    fun onClick(result: Shopping) = clickListener(result.idx)
}

