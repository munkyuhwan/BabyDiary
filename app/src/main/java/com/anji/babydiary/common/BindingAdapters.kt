package com.anji.babydiary.common

import android.widget.TextView
import androidx.databinding.BindingAdapter

object BindingAdapters  {

    @BindingAdapter("setFormattedDate")
    fun setFormattedDate(view: TextView, text:String) {
        view.text = ".${text}"
    }

}