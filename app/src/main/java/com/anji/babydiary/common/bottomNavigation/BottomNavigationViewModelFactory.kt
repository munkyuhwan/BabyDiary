package com.anji.babydiary.common.bottomNavigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anji.babydiary.common.BaseActivity
import java.lang.IllegalArgumentException

class BottomNavigationViewModelFactory(val activity:BaseActivity, val idx:Int) :ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(BottomNavigationViewModel::class.java)){
            return BottomNavigationViewModel(activity, idx) as T
        }
        throw IllegalArgumentException()

    }

}