package com.anji.babydiary.common.bottomNavigation

import android.content.Intent
import android.view.View
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


    var eventDot = MutableLiveData<Int>()
    var shoppingDot =  MutableLiveData<Int>()
    var mainDot =  MutableLiveData<Int>()
    var tipDot =  MutableLiveData<Int>()
    var myPageDot =  MutableLiveData<Int>()

    init {

        when(idx) {
            0 -> {
                eventRadioChecked.value = true
                eventDot.value = View.VISIBLE
                shoppingDot.value = View.GONE
                mainDot.value = View.GONE
                tipDot.value = View.GONE
                myPageDot.value = View.GONE
            }
            1 -> {
                shoppingRadioChecked.value = true
                eventDot.value = View.GONE
                shoppingDot.value = View.VISIBLE
                mainDot.value = View.GONE
                tipDot.value = View.GONE
                myPageDot.value = View.GONE
            }
            2 -> {
                mainRadioChecked.value = true
                eventDot.value = View.GONE
                shoppingDot.value = View.GONE
                mainDot.value = View.VISIBLE
                tipDot.value = View.GONE
                myPageDot.value = View.GONE
            }
            3 -> {
                tipRadioChecked.value = true
                eventDot.value = View.GONE
                shoppingDot.value = View.GONE
                mainDot.value = View.GONE
                tipDot.value = View.VISIBLE
                myPageDot.value = View.GONE
            }
            4 -> {
                myPageRadioChecked.value = true
                eventDot.value = View.GONE
                shoppingDot.value = View.GONE
                mainDot.value = View.GONE
                tipDot.value = View.GONE
                myPageDot.value = View.VISIBLE
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