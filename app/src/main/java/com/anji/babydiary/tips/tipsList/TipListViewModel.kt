package com.anji.babydiary.tips.tipsList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.anji.babydiary.database.shopping.TipDao
import com.anji.babydiary.database.shopping.TipDatabase

class TipListViewModel(
    var database: TipDao,
    application:Application
) : AndroidViewModel(application) {

    init {

    }

}



