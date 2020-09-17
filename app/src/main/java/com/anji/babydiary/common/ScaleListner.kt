package com.anji.babydiary.common

import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener
import android.widget.ImageView


 class ScaleListener(val mImageView: ImageView) : SimpleOnScaleGestureListener() {
    // when a scale gesture is detected, use it to resize the image
    private var mScaleFactor: Float = 1.0f
    //private val mImageView: ImageView? = null


    override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {
        mScaleFactor *= scaleGestureDetector.scaleFactor
        mImageView!!.setScaleX(mScaleFactor)
        mImageView!!.setScaleY(mScaleFactor)
        return true
    }
}