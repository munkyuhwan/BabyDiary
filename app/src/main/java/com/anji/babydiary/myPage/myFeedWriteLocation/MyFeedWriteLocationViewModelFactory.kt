package com.anji.babydiary.myPage.myFeedWriteLocation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class MyFeedWriteLocationViewModelFactory :ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MyFeedWriteLocationViewModel::class.java)) {
            return MyFeedWriteLocationViewModel() as T
        }
        throw IllegalArgumentException()
    }

}