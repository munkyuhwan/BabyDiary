package com.anji.babydiary.myPage.themeSetting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anji.babydiary.R

class ThemeSettingViewModel : ViewModel() {

    var isLightSelected = MutableLiveData<Boolean>()
    var isDarkSelected = MutableLiveData<Boolean>()

    var theme = MutableLiveData<String>()

    init {
        isLightSelected.value = true
        isDarkSelected.value = false
        theme.value = "#ffffff"
    }

    fun onThemeSelect(sel:Int) {

        when(sel) {
            0 -> {
                isLightSelected.value = true
                isDarkSelected.value = false
                theme.value = "#ffffff"
            }
            1 -> {
                isLightSelected.value = false
                isDarkSelected.value = true
                theme.value = "#3B3939"
            }
        }

    }



}