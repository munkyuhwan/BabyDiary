package com.anji.babydiary.common

import android.R
import android.content.ContentResolver
import android.content.Context
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.net.URI
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


object Utils {
    fun getMilliFromDate(dateFormat: String?): Long {
        var date = Date()
        val formatter = SimpleDateFormat("yyyy MM dd HH:mm")
        try {
            date = formatter.parse(dateFormat)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return date.time
    }

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

    fun saveBitmapToJpeg(bitmap: Bitmap, name: String, dir:File): Uri? {

        //내부저장소 캐시 경로를 받아옵니다.

        Log.e("dir","${dir}")

        val storage: File = dir
        storage.mkdir()

        //저장할 파일 이름
        val fileName = "$name.jpg"

        //storage 에 파일 인스턴스를 생성합니다.
        val tempFile = File(storage, fileName)
        try {


            // 자동으로 빈 파일을 생성합니다.
            tempFile.createNewFile()

            // 파일을 쓸 수 있는 스트림을 준비합니다.
            val out = FileOutputStream(tempFile)

            // compress 함수를 사용해 스트림에 비트맵을 저장합니다.
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)

            // 스트림 사용후 닫아줍니다.
            out.close()
            return tempFile.toUri()
        } catch (e: FileNotFoundException) {
            Log.e("MyTag", "FileNotFoundException : " + e.message)
            return null
        } catch (e: IOException) {
            Log.e("MyTag", "IOException : " + e.message)
            return null
        }
    }

    fun showAlert(context:Context, title:String ,msg:String, navController: NavController) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(
            ContextThemeWrapper(
                context,
                R.style.Theme_Material_Light_Dialog_NoActionBar
            )
        )
        builder.setTitle("${title}")
        builder.setMessage("${msg}")
        builder.setPositiveButton("확인",
            DialogInterface.OnClickListener { dialog, id ->

                navController.popBackStack()

            })

        builder.setNegativeButton("취소",
            DialogInterface.OnClickListener { dialog, id -> })
        builder.show()
    }

}