package com.anji.babydiary.shopping.listFragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anji.babydiary.database.shopping.ShoppingDao

class ShopListViewModelFactory(private val database: ShoppingDao, private val application: Application):ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShopListViewModel::class.java)) {
            return ShopListViewModel(database, application) as T
        }
        throw IllegalArgumentException()
    }

}