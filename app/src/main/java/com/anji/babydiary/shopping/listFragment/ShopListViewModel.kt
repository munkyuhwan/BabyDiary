package com.anji.babydiary.shopping.listFragment

import android.app.Application
import android.util.Log
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

    fun selectByCat(type:Int) {

        if (type == 0 ) {
            uiScope.launch {
                query("recomm")
            }
        }else {
            uiScope.launch {
                query("second")
            }
        }


    }

    suspend fun query(keyword: String) {
        withContext(Dispatchers.IO) {
            allProduct = database.selectAllByKeyword(keyword)
        }
    }

    fun saveData(title:CharSequence, price:CharSequence, url:CharSequence, image:String, type:String) {

        var shopData = Shopping()
        shopData.title = title.toString()
        shopData.url = url.toString()
        shopData.price = price.toString()
        shopData.imgDir = image
        shopData.productType = type
        uiScope.launch {
            insertData(shopData)
        }
    }


    private suspend fun insertData(shopdata:Shopping) {
        withContext(Dispatchers.IO) {
            database.insert(shopdata)
        }
    }

}

class ProductClickListener(val clickListener:(resultId:Long)->Unit ) {
    fun onClick(result: Shopping) = clickListener(result.idx)
}

