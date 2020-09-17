package com.anji.babydiary.common

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.net.URI
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


    fun getScreenShotFromView(v: View): Bitmap? {
        var screenshot: Bitmap? = null
        try {
            if (v != null) {
                screenshot = Bitmap.createBitmap(v.measuredWidth, v.measuredHeight, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(screenshot)
                v.draw(canvas)
            }
        } catch (e: Exception) {
            Log.e("BLABLA", "Failed to capture screenshot because:" + e.message)
        }
        return screenshot
    }

    fun saveBitmap(myBitmap: Bitmap?, contentResolver: ContentResolver):URI? {
        try {
            if (myBitmap != null) {
                //storeScreenShot(myBitmap, "test2.jpg", this@TelecollecteActivity)
                val extr = "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString()}"
                val myPath = File(extr, "${System.currentTimeMillis()}.jpg")
                var fos: FileOutputStream? = null


                try {
                    myPath.mkdirs()
                    myPath.createNewFile()
                    fos = FileOutputStream(myPath)

                    myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                    fos!!.flush()
                    fos!!.close()

                    MediaStore.Images.Media.insertImage(contentResolver, myBitmap,
                        "Screen", "screen")

                    return myPath.toURI()
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                    return null
                } catch (e: Exception) {
                    e.printStackTrace()
                    return null
                }

            }
        } catch (e: java.lang.Exception) {
            Log.e("BLABLA", "Error ::" + e.message)
            return null
        }
        return null

    }



}