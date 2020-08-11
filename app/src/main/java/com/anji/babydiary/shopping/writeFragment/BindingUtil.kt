package com.anji.babydiary.shopping.writeFragment

import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("selectedImage")
fun loadImageWithUri(imageView: ImageView, imageUri: String){
    Glide.with(imageView.context).load(Uri.parse(imageUri)).into(imageView)
}