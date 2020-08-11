package com.anji.babydiary.shopping.writeFragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anji.babydiary.database.shopping.ShoppingDao
import com.anji.babydiary.shopping.listFragment.ShopListViewModel

class WriteProductViewModelFactory(val database:ShoppingDao, private val application: Application):ViewModelProvider.Factory{

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WriteProductViewModel::class.java)) {
            return WriteProductViewModel(database, application) as T
        }
        throw IllegalArgumentException()
    }

}