package com.anji.babydiary.myPage.myFeedWrite.templateSelect

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TemplateSelectViewModel(application: Application) : AndroidViewModel(application) {

    var selectedTemplate = MutableLiveData<Int>()

    init {

    }

    fun selectTemplate(sel:Int) {
        selectedTemplate.value = sel
    }


}