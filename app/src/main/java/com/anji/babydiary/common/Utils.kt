package com.anji.babydiary.common

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import java.text.SimpleDateFormat
import java.util.*

object Utils {


    fun getDate(milliSeconds: Long, dateFormat: String?): String? {
        // Create a DateFormatter object for displaying date in specified format.
        val formatter = SimpleDateFormat(dateFormat)

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar: Calendar = Calendar.getInstance()
        calendar.setTimeInMillis(milliSeconds)
        return formatter.format(calendar.getTime())
    }

    fun convertToTime(seconds:String):String {
        val minSecond = 60
        var min =  String.format("%02d", seconds.toInt()/minSecond)
        var second = String.format("%02d", seconds.toInt()%minSecond)

        return "${min}:${second}"
    }


    fun setFeedListImg(view: ShapeableImageView) {
        val radius: Float = 50f
        // activity.getResources().getDimension(R.dimen.default_corner_radius)
        view.setShapeAppearanceModel(
            view.getShapeAppearanceModel()
                .toBuilder()
                //.setTopRightCorner(CornerFamily.ROUNDED, radius)
                .setBottomLeftCorner(CornerFamily.ROUNDED, radius)
                .build()
        )
    }

    fun setMyFeedListImg(view: ShapeableImageView) {
        val radius: Float = 50f
        // activity.getResources().getDimension(R.dimen.default_corner_radius)
        view.setShapeAppearanceModel(
            view.getShapeAppearanceModel()
                .toBuilder()
                .setTopRightCorner(CornerFamily.ROUNDED, radius)
                .setBottomLeftCorner(CornerFamily.ROUNDED, radius)
                .build()
        )
    }


}