package com.anji.babydiary.tips.tipsList

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anji.babydiary.database.tip.TipsDao
import java.lang.IllegalArgumentException

class TipListViewModelFactory(val database: TipsDao, val application:Application) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TipListViewModel::class.java)) {
            return TipListViewModel(database, application) as T
        }
        throw IllegalArgumentException()
    }

}