package com.anji.babydiary.common

import android.view.ViewGroup.MarginLayoutParams
import android.widget.ImageButton
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.anji.babydiary.R
import com.anji.babydiary.dailyCheck.DailyCheckListObj
import com.anji.babydiary.database.likes.Likes
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import kotlinx.android.synthetic.main.daily_check_write_fragment.view.*


object BindingAdapters  {

    @BindingAdapter("setFormattedDate")
    fun setFormattedDate(view: TextView, text:String) {
        view.text = ".${text}"
    }

    @BindingAdapter("setSelectedIcon")
    @JvmStatic
    fun setSelectedIcon(view:ImageButton, selectedIndex:Int) {
        view.background = view.context.getDrawable(DailyCheckListObj.itemBackground[selectedIndex])
        view.setImageResource(DailyCheckListObj.itemSrc[selectedIndex])
    }

    @BindingAdapter("setFormattedDate")
    @JvmStatic
    fun setFormattedDate(view:TextView, timeLong:Long) {
        view.setText(Utils.getDate(timeLong, "YYYY. mm . dd"))
    }

    @BindingAdapter("setVibeText")
    @JvmStatic
    fun setVibeNumText(view:TextView, num:Likes) {
        view.setText("공감 ${num}개")
    }

    @BindingAdapter("setCountUp")
    @JvmStatic
    fun setCountUp(view:TextView, seconds:Int) {

    }

    @BindingAdapter("setDate")
    @JvmStatic
    fun setDate(view:TextView, date: MutableLiveData<String>) {
        if (date.value != null) {
            view.setText(".${date.value}")
        }else {
            view.setText("")
        }
    }

    @BindingAdapter("layout_marginLeft")
    @JvmStatic
    fun setLayoutMarginLeft(view: RecyclerView, marginTop: Float) {
        if (view.layoutParams is MarginLayoutParams) {
            val p = view.layoutParams as MarginLayoutParams
            p.setMargins(marginTop.toInt(), 0, 0, 0)
            view.requestLayout()
        }
    }

    @BindingAdapter("setCountingText")
    @JvmStatic
    fun setCountingText(view:TextView, count:Int) {
        val minSecond = 60
        var min =  String.format("%02d", count/minSecond)
        var second = String.format("%02d", count%minSecond)
        if(count > 0) {
            view.setText( "${min}:${second}")
        }else {
            if (view.id == R.id.leftTime) {
                view.setText("왼쪽")
            }else {
                view.setText("오른쪽")
            }
        }
    }




}