package com.anji.babydiary.myPage.myFeedWrite.templateSelect

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class TemplateSelectViewModelFactory(val application: Application):ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TemplateSelectViewModel::class.java)) {
            return TemplateSelectViewModel(application) as T
        }
        throw IllegalArgumentException();
    }


}