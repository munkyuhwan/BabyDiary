package com.anji.babydiary.bottomActivity

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.ColorUtils
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator
import com.anji.babydiary.R
import com.anji.babydiary.common.CommonCode
import com.anji.babydiary.databinding.ActivityBottomMenuBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import kotlinx.coroutines.selects.select


class BottomMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityBottomMenuBinding>(this, R.layout.activity_bottom_menu)

        val application = requireNotNull(this).application

        val viewModelFactory = BottomMenuViewModelFactory(application)
        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(BottomMenuViewModel::class.java)

        binding.dimLayer.setOnClickListener {
            finish()
        }
        var selectedItem:Int = -1

        binding.alarmBtn.setOnClickListener {
            selectedItem = 0
            finishWithResult(selectedItem)
        }

        binding.myAccountBtn.setOnClickListener {
            selectedItem = 1
            finishWithResult(selectedItem)
        }
        binding.spouserBtn.setOnClickListener {
            selectedItem = 2
            finishWithResult(selectedItem)
        }
        binding.themeSettingBtn.setOnClickListener {
            selectedItem = 3
            finishWithResult(selectedItem)
        }
        binding.resignBtn.setOnClickListener {
            selectedItem = 4
            finishWithResult(selectedItem)
        }
        binding.logOutBtn.setOnClickListener {
            selectedItem = 5
            finishWithResult(selectedItem)
        }

    }

    private fun finishWithResult(selectedItem:Int) {

        var intentResult: Intent = Intent()
        intentResult.putExtra("selectedItem", selectedItem)
        setResult(Activity.RESULT_OK, intentResult)
        finish()
    }



    private fun setWindowFlag(activity: Activity, on: Boolean) {
        val win = activity.window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        } else {
            winParams.flags = winParams.flags and WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS.inv()
        }
        win.attributes = winParams
    }
}