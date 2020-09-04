package com.anji.babydiary.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.anji.babydiary.common.CommonCode
import com.anji.babydiary.database.profile.ProfileDao

class LoginViewModel (val database: ProfileDao, application: Application):AndroidViewModel(application) {

    val idx = MutableLiveData<Long>()

    init {

        idx.value = CommonCode.USER_IDX

    }


    fun login(userIdx:CharSequence){
        CommonCode.USER_IDX = userIdx.toString().toLong()
        idx.value = CommonCode.USER_IDX
    }

}