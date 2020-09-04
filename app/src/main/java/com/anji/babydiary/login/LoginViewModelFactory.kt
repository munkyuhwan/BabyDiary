package com.anji.babydiary.login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anji.babydiary.database.profile.ProfileDao
import com.anji.babydiary.database.profile.Profiles
import java.lang.IllegalArgumentException

class LoginViewModelFactory(val database:ProfileDao, val application:Application) :ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LoginViewModel(database, application)::class.java )) {
            return LoginViewModel(database, application) as T
        }
        throw IllegalArgumentException()

    }

}