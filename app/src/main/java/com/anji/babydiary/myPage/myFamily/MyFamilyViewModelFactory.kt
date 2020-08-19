package com.anji.babydiary.myPage.myFamily

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anji.babydiary.database.family.FamilyDao
import com.anji.babydiary.database.family.FamilyDatabase
import java.lang.IllegalArgumentException

class MyFamilyViewModelFactory(val database: FamilyDao, val application: Application ) :ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(MyFamilyViewModel::class.java)) {
            return MyFamilyViewModel(database, application) as T
        }

        throw IllegalArgumentException()
    }

}