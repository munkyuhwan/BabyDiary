package com.anji.babydiary.shopping.listFragment

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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


    var allProduct = MutableLiveData<List<Shopping>>()
    var allProductCheck = MutableLiveData<List<Shopping>>()

    var isCategoryOpen = MutableLiveData<Int>()
    var arrowRotation = MutableLiveData<Float>()

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        isCategoryOpen.value = View.GONE
        uiScope.launch {
            queryAll()
        }
        uiScope.launch {
            queryAllCheck()
        }
        arrowRotation.value = 0F

    }

    suspend fun queryAll(){
        withContext(Dispatchers.IO) {
            allProduct.postValue(database.selectAll())
            arrowRotation.postValue(0F)
        }
    }

    suspend fun queryAllCheck(){
        withContext(Dispatchers.IO) {
            allProductCheck.postValue(database.selectAll())
        }
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



    fun selectByCat(type:Int) {

        if (type == 0 ) {
            uiScope.launch {
                onCategorySelectClicked()
                query("recomm")
            }
        }else {
            uiScope.launch {
                onCategorySelectClicked()
                query("second")
            }
        }

    }

    suspend fun query(keyword: String) {
        withContext(Dispatchers.IO) {
            allProduct.postValue( database.selectAllByType(keyword) )
        }
    }

    fun selectByKeyword(keyword: String) {
        uiScope.launch {
            queryKeyword(keyword)
        }
    }
    suspend fun queryKeyword(keyword: String) {
        withContext(Dispatchers.IO) {
            allProduct.postValue(database.selectAllByKeyword(keyword))
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

    fun onCategorySelectClicked() {
        if (isCategoryOpen.value == View.GONE ) {
            isCategoryOpen.value = View.VISIBLE
            arrowRotation.value = 180F
        }else {
            isCategoryOpen.value = View.GONE
            arrowRotation.value = 0F
        }

    }
}

class ProductClickListener(val clickListener:(resultId:Long)->Unit ) {
    fun onClick(result: Shopping) = clickListener(result.idx)
}

