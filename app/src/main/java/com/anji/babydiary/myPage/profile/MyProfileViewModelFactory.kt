package com.anji.babydiary.myPage.profile

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anji.babydiary.database.profile.Profile
import com.anji.babydiary.database.profile.ProfileDao
import java.lang.IllegalArgumentException

class MyProfileViewModelFactory(val database: ProfileDao, val application: Application): ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyProfileViewModel::class.java)) {
            return MyProfileViewModel(database, application) as T
        }

        throw IllegalArgumentException()
    }

}