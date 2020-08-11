package com.anji.babydiary.mainFeed

import android.app.Application
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.transition.Visibility

class MainFeedViewModel (application:Application):AndroidViewModel(application) {

    var isOpen = MutableLiveData<Boolean>()
    var isVisible = MutableLiveData<Int>()

    var isMain = MutableLiveData<Boolean>()
    var isPop = MutableLiveData<Boolean>()
    var isShopping = MutableLiveData<Boolean>()
    var isTip = MutableLiveData<Boolean>()
    var isMy = MutableLiveData<Boolean>()

    init {
        isOpen.value = false
        isVisible.value = View.GONE
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

    fun goPop() {
    }
    fun goShopping() {

    }

    fun goTip() {

    }

    fun goMy() {

    }

}


