package com.anji.babydiary.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.anji.babydiary.common.CommonCode
import com.anji.babydiary.common.Utils
import com.anji.babydiary.database.profile.ProfileDao
import okhttp3.internal.Util

class LoginViewModel (val database: ProfileDao, application: Application):AndroidViewModel(application) {

    val idx = MutableLiveData<Long>()

    init {

        idx.value = 0L

    }


    fun login(userIdx:CharSequence){
        idx.value = userIdx.toString().toLong()
    }

}