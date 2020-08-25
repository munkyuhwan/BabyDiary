package com.anji.babydiary.common

import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginTop
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anji.babydiary.common.dailyCheck.DailyCheckListObj
import com.anji.babydiary.database.likes.Likes
import com.anji.babydiary.generated.callback.OnClickListener
import com.anji.babydiary.tips.TipActivity
import com.bumptech.glide.load.engine.Resource

object BindingAdapters:AppCompatActivity()  {

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


}