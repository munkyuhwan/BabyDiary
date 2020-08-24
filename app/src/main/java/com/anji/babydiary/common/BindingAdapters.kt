package com.anji.babydiary.common

import android.content.Context
import android.opengl.Visibility
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.view.marginTop
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anji.babydiary.common.dailyCheck.DailyCheckListObj
import com.bumptech.glide.load.engine.Resource

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


}