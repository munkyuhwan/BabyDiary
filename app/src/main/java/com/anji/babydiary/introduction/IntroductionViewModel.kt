package com.anji.babydiary.introduction

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.anji.babydiary.common.CommonCode
import kotlin.random.Random

class IntroductionViewModel(application: Application): AndroidViewModel(application) {

    var selectedSentence:String = ""
    val random = Random

    init {
        Random(5)
        var rnd = rand(0,5)
        Log.e("dddd","randnum: ${rnd}")
        selectedSentence = CommonCode.INTRODUCTION[rnd]
    }

    fun rand(from: Int, to: Int) : Int {
        return random.nextInt(to - from) + from
    }
}