package com.anji.babydiary.common.dailyCheck

import android.opengl.Visibility
import android.view.View
import com.anji.babydiary.R

object DailyCheckListObj  {
    val itemBackground = arrayOf(
        R.drawable.circle_orange,
        R.drawable.circle_orange,
        R.drawable.circle_orange,

        R.drawable.circle_green,
        R.drawable.circle_green,
        R.drawable.circle_green,
        R.drawable.circle_green,
        R.drawable.circle_green,
        R.drawable.circle_green,

        R.drawable.circle_blue,
        R.drawable.circle_blue,
        R.drawable.circle_blue,
        R.drawable.circle_blue,

        R.drawable.circle_purple,
        R.drawable.circle_purple,
        R.drawable.circle_purple,
        R.drawable.circle_purple

    )
    val itemSrc = arrayOf(
        R.drawable.weight_icon,
        R.drawable.height_icon,
        R.drawable.head_size_icon,

        R.drawable.breast_feed_icon,
        R.drawable.breast_icon,
        R.drawable.breast_amount,
        R.drawable.powder_icon,
        R.drawable.food_icon,
        R.drawable.side_food_icon,

        R.drawable.diaper_icon,
        R.drawable.sleep_icon,
        R.drawable.bath_icon,
        R.drawable.play_icon,


        R.drawable.hospital_icon,
        R.drawable.temperature_icon,
        R.drawable.pill_icon,
        R.drawable.injection_icon
    )

    val itemName = arrayOf(
        "몸무게",
        "키",
        "머리둘레",
        "수유",
        "유축수유",
        "유축",
        "분유",
        "이유식",
        "간식",
        "배변",
        "수면",
        "목욕",
        "놀이",
        "병원",
        "체온",
        "약",
        "주사/예방접종",
        "기타"
    )
    //true 메모일경우, false 타이머경
    val itemInput = arrayOf(
        true,
        true,
        true,
        false,
        false,
        false,
        true,
        true,
        true,
        true,
        false,
        true,
        true,
        true,
        true,
        true,
        true,
        true
    )

}