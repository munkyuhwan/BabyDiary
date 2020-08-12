package com.anji.babydiary.common

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
class OpenGallery private constructor() {

    private val PERMISSION_CODE = 9898
    private val IMAGE_PICK_CODE = 1000;

    companion object {
        @Volatile private var instance: OpenGallery? = null

        @JvmStatic fun getInstance(): OpenGallery =
            instance ?: synchronized(this) {
                instance ?: OpenGallery().also {
                    instance = it
                }
            }
    }


    fun permissionCheck( application:Application,  activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ContextCompat.checkSelfPermission(
                    application,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) ==
                PackageManager.PERMISSION_DENIED){
                //permission denied
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                //show popup to request runtime permission

                requestPermissions(activity, permissions, PERMISSION_CODE);
            }
            else{
                //permission already granted
                pickImageFromGallery(activity);
            }
        }
        else{
            //system OS is < Marshmallow
            pickImageFromGallery(activity);
        }
    }


    private fun pickImageFromGallery(activity: Activity) {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        activity.startActivityForResult(intent, IMAGE_PICK_CODE)
    }

}