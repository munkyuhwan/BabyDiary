package com.anji.babydiary.mainFeed

import android.app.Application
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.CalendarView
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.BindingAdapter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.transition.Visibility

class MainFeedViewModel (application:Application):AndroidViewModel(application) {

    var isOpen = MutableLiveData<Boolean>()
    var isVisible = MutableLiveData<Int>()


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



    fun onDateSelected(view: CalendarView) {

        Log.e("calendar", "============================================================================");
        Log.e("calendar", "${view.date}");
        Log.e("calendar", "============================================================================");
    }

}


