package com.anji.babydiary.introduction

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.anji.babydiary.common.CommonCode

class IntroductionViewModel(application: Application): AndroidViewModel(application) {

    var selectedSentence:String = ""

    init {
        var rnd = Math.random()%CommonCode.INTRODUCTION.size

        selectedSentence = CommonCode.INTRODUCTION[rnd.toInt()]

    }

}