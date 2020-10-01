package com.anji.babydiary.shopping.detailFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anji.babydiary.database.shopping.Shopping
import com.anji.babydiary.database.shopping.ShoppingDao

class ShoppingDetailViewModel(val database: ShoppingDao, val idx:Long, application: Application) : AndroidViewModel(application) {

    val data = database.selectProduct(idx)

    val url = MutableLiveData<String>()

    init {
        url.value = ""
    }


}