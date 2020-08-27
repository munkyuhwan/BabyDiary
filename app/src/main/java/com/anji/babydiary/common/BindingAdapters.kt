package com.anji.babydiary.common

import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.anji.babydiary.dailyCheck.DailyCheckListObj
import com.anji.babydiary.database.likes.Likes

object BindingAdapters  {

    @BindingAdapter("setFormattedDate")
    fun setFormattedDate(view: TextView, text:String) {
        view.text = ".${text}"
    }

    @BindingAdapter("setSelectedIcon")
    @JvmStatic
    fun setSelectedIcon(view:ImageButton, selectedIndex:Int) {
        view.background = view.context.getDrawable(DailyCheckListObj.itemBackground[selectedIndex])
        view.setImageResource(DailyCheckListObj.itemSrc[selectedIndex])
    }

    @BindingAdapter("setFormattedDate")
    @JvmStatic
    fun setFormattedDate(view:TextView, timeLong:Long) {
        view.setText(Utils.getDate(timeLong, "YYYY. mm . dd"))
    }

    @BindingAdapter("setVibeText")
    @JvmStatic
    fun setVibeNumText(view:TextView, num:Likes) {
        view.setText("공감 ${num}개")
    }

    @BindingAdapter("setCountUp")
    @JvmStatic
    fun setCountUp(view:TextView, seconds:Int) {

    }

    @BindingAdapter("setDate")
    @JvmStatic
    fun setDate(view:TextView, date: MutableLiveData<String>) {
        if (date.value != null) {
            view.setText(".${date.value}")
        }else {
            view.setText("")
        }
    }

}