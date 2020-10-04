package com.anji.babydiary.introduction

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.anji.babydiary.common.CommonCode

class IntroductionViewModel(application: Application): AndroidViewModel(application) {

    var selectedSentence:String = ""

    init {
        var rnd = Math.abs( (Math.random()%CommonCode.INTRODUCTION.size)*10 )
        Log.e("dddd","randnum: ${rnd}")
        selectedSentence = CommonCode.INTRODUCTION[rnd.toInt()]
    }

}