package com.anji.babydiary.shopping.listFragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anji.babydiary.database.shopping.ShoppingDao
import com.anji.babydiary.database.shopping.shoppingBookmark.ShoppingBookMarkDao

class ShopListViewModelFactory(private val database: ShoppingDao, private val bookmarkDatabase:ShoppingBookMarkDao, private val application: Application):ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShopListViewModel::class.java)) {
            return ShopListViewModel(database, bookmarkDatabase, application) as T
        }
        throw IllegalArgumentException()
    }

}