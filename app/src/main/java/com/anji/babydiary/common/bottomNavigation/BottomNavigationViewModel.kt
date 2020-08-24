package com.anji.babydiary.common.bottomNavigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anji.babydiary.common.BaseActivity
import com.anji.babydiary.event.EventActivity
import com.anji.babydiary.mainFeed.MainFeedActivity
import com.anji.babydiary.myPage.MyPage
import com.anji.babydiary.shopping.ShoppingActivity
import com.anji.babydiary.tips.TipActivity

class BottomNavigationViewModel(val activity:BaseActivity, val idx: Int) :ViewModel() {

    var eventRadioChecked = MutableLiveData<Boolean>()
    var shoppingRadioChecked =  MutableLiveData<Boolean>()
    var mainRadioChecked =  MutableLiveData<Boolean>()
    var tipRadioChecked =  MutableLiveData<Boolean>()
    var myPageRadioChecked =  MutableLiveData<Boolean>()

    init {
        when(idx) {
            0 -> {
                eventRadioChecked.value = true
            }
            1 -> {
                shoppingRadioChecked.value = true
            }
            2 -> {
                mainRadioChecked.value = true
            }
            3 -> {
                tipRadioChecked.value = true
            }
            4 -> {
                myPageRadioChecked.value = true
            }
        }
    }

    fun goEvent() {
        activity.eventIntent()
    }

    fun goShopping() {
        activity.shoppingIntent()

    }

    fun goMain() {
        activity.mainIntent()

    }
    fun goTip() {
        activity.tipIntent()
    }
    fun goMyPage() {
        activity.myPageIntent()

    }


}