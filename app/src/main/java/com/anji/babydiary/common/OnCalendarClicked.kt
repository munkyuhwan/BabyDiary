package com.anji.babydiary.common

import android.util.Log
import android.widget.CalendarView
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter

object OnCalendarClicked: AppCompatActivity() {


    @BindingAdapter("onCalendarSelected")
    fun CalendarView.onCalendarClick(view: CalendarView) {
        Log.e("calendar", "============================================================================");
        Log.e("calendar", "${view.date}");
        Log.e("calendar", "============================================================================");
    }

}