package com.anji.babydiary.gnb.main

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NavViewModel(application: Application):AndroidViewModel(application) {

    var isOpen = MutableLiveData<Boolean>()
    var isVisible = MutableLiveData<Int>()

    var isPopular = MutableLiveData<Boolean>()
    var isShopping = MutableLiveData<Boolean>()
    var isMain = MutableLiveData<Boolean>()
    var isTip = MutableLiveData<Boolean>()
    var isMyPage = MutableLiveData<Boolean>()

    init {
        isOpen.value = false
        isVisible.value = View.GONE

        isPopular.value = false
        isShopping.value = false
        isMain.value = false
        isTip.value = false
        isMyPage.value = false
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