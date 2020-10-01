package com.anji.babydiary.shopping.detailFragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anji.babydiary.database.shopping.Shopping
import com.anji.babydiary.database.shopping.ShoppingDao

class ShoppingDetailViewModelFactory(val database: ShoppingDao, val idx:Long, val application: Application) :ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ShoppingDetailViewModel(database, idx, application) as T
    }

}