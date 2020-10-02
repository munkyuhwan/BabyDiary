package com.anji.babydiary.shopping.listFragment

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.anji.babydiary.common.CommonCode
import com.anji.babydiary.common.MyShare.MyShare
import com.anji.babydiary.database.mainFeed.MainFeed
import com.anji.babydiary.database.shopping.Shopping
import com.anji.babydiary.database.shopping.ShoppingDao
import com.anji.babydiary.database.shopping.shoppingBookmark.ShoppingBookMark
import com.anji.babydiary.database.shopping.shoppingBookmark.ShoppingBookMarkDao
import com.anji.babydiary.database.tip.tipsBookmark.TipBookMark
import kotlinx.coroutines.*

class ShopListViewModel(
    val database:ShoppingDao,
    val bookmarkDatabase:ShoppingBookMarkDao,
    application: Application
    ) : AndroidViewModel(application) {

    var bookmarkTipIdx:Long = 0

    var bookMarks = MutableLiveData<List<ShoppingBookMark>>()
    var allProduct = MutableLiveData<List<Shopping>>()
    var allProductCheck = MutableLiveData<List<Shopping>>()

    var isCategoryOpen = MutableLiveData<Int>()
    var arrowRotation = MutableLiveData<Float>()
    var singleFeed = ArrayList<Shopping>()

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    var seletBookMark = MutableLiveData<List<ShoppingBookMark>>()

    var selectedCategory = MutableLiveData<String>()

    init {
        isCategoryOpen.value = View.GONE
        uiScope.launch {
            queryAll()
        }
        uiScope.launch {
            queryAllCheck()
        }
        arrowRotation.value = 0F
        selectedCategory.value = "전체보기"
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

        if (type == 0) {
            selectedCategory.value = "추천용품"
        }else if (type == 1) {
            selectedCategory.value = "중고용품"

        }else {
            selectedCategory.value = "전체보기"
        }

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



    fun selectBookmark() {
        uiScope.launch {
            querySelectBookmark()
        }
    }
    suspend fun querySelectBookmark() {
        withContext(Dispatchers.IO) {
            bookMarks.postValue( bookmarkDatabase.selectByUserIdx(MyShare.prefs.getLong("idx", 0)) )
        }
    }

    fun selectBookmarkedFeed(idx:Long) {
        uiScope.launch {
            querySelectBookmarkedFeed(idx)
        }
    }
    suspend fun querySelectBookmarkedFeed(idx:Long) {
        withContext(Dispatchers.IO) {
            singleFeed.add(database.selectSingleMut(idx))
            allProduct.postValue(singleFeed)
        }
    }

    fun selectBookmark(tipIdx:Long) {
        bookmarkTipIdx = tipIdx
        uiScope.launch {
            querySelectBookmark(tipIdx)
        }
    }

    suspend fun querySelectBookmark(tipIdx:Long){
        withContext(Dispatchers.IO) {
            seletBookMark.postValue( bookmarkDatabase.selectByShoppingAndUser(MyShare.prefs.getLong("idx", 0), tipIdx) )
        }
    }

    fun addBookmark(shoppingIdx:Long) {

        var shoppingBookmark = ShoppingBookMark()
        shoppingBookmark.shoppingIdx = shoppingIdx
        shoppingBookmark.userIdx = MyShare.prefs.getLong("idx", 0)

        uiScope.launch {
            queryAddBookmark(shoppingBookmark)
        }
    }

    suspend fun queryAddBookmark(shoppingBookmark: ShoppingBookMark) {
        withContext(Dispatchers.IO) {
            bookmarkDatabase.insert(shoppingBookmark)
        }
    }


    fun deleteBookmark(tipIdx: Long) {
        uiScope.launch {
            queryDeleteBookmark(tipIdx)
        }
    }
    suspend fun queryDeleteBookmark(tipIdx: Long) {
        withContext(Dispatchers.IO) {
            bookmarkDatabase.deleteBookmark(MyShare.prefs.getLong("idx", 0), tipIdx)
        }
    }


}

class ProductClickListener(val clickListener:(resultId:Long)->Unit ) {
    fun onClick(result: Shopping) = clickListener(result.idx)
}


class ShoppingBookMarkClickListener(val shoppingBookMarkClickListener:(idx:Long)->Unit) {
    fun onShoppingBookMarkClick(result:Shopping) = shoppingBookMarkClickListener(result.idx)
}

