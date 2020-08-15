package com.anji.babydiary.event.eventList.listAdapter

import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.BindingAdapter

@BindingAdapter("onImageClicked")
fun ImageView.setOnImageClicked(idx:Long) {
    idx?.let {
        Toast.makeText(context, "${idx}", Toast.LENGTH_SHORT).show()
    }
}