package com.anji.babydiary.shopping

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class ShopActivityViewModel(application: Application):AndroidViewModel(application) {

    var isOpen = MutableLiveData<Boolean>()
    var isVisible = MutableLiveData<Int>()

    init {

    }

    fun onCategoryClicked() {

        if (isOpen.value == false) {
            isOpen.value = true
            isVisible.value = View.VISIBLE
        }else {
            isOpen.value = false
            isVisible.value = View.GONE
        }
    }

}