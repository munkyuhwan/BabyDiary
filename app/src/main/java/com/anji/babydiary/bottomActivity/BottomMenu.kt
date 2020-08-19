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
import jp.wasabeef.blurry.Blurry
import kotlinx.coroutines.selects.select


class BottomMenu : AppCompatActivity() {


    /*
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<ActivityBottomMenuBinding>(inflater, R.layout.activity_bottom_menu, container, false)

        val application = requireNotNull(this.activity).application

        val viewModelFactory = BottomMenuViewModelFactory(application)
        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(BottomMenuViewModel::class.java)


        return binding.root
    }

     */


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
        }

        binding.myAccountBtn.setOnClickListener {
            selectedItem = 1
        }
        binding.spouserBtn.setOnClickListener {
            selectedItem = 2
        }
        binding.themeSettingBtn.setOnClickListener {
            selectedItem = 3
        }
        binding.resignBtn.setOnClickListener {
            selectedItem = 4
        }

        var intentResult: Intent = Intent()
        intent.putExtra("selectedItem", selectedItem)



        setResult(Activity.RESULT_OK, intentResult)


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