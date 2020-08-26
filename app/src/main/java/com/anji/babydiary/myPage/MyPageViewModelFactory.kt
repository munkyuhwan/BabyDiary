package com.anji.babydiary.myPage

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anji.babydiary.database.profile.ProfileDao
import com.anji.babydiary.database.profile.ProfileDatabase
import java.lang.IllegalArgumentException

class MyPageViewModelFactory (val database:ProfileDao, val idx:Long, val application: Application):ViewModelProvider.Factory{

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MyPageViewModel::class.java)) {
            return MyPageViewModel(database, idx, application) as T
        }
        throw IllegalArgumentException()
    }

}