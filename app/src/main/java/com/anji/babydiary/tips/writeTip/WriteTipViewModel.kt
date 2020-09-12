package com.anji.babydiary.tips.writeTip

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.anji.babydiary.common.CommonCode
import com.anji.babydiary.database.tip.Tips
import com.anji.babydiary.database.tip.TipsDao
import kotlinx.coroutines.*

class WriteTipViewModel(val database: TipsDao, application:Application) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    var selectedImage = MutableLiveData<Uri>()


    var isDone = MutableLiveData<Boolean>()

    val categories = MutableLiveData<Array<String>>()
    var selectedCategory = ""
    init {
        isDone.value = false
        categories.value = CommonCode.TIP_CATEGORY
    }

    fun insertTip(text:CharSequence) {
        var tip = Tips()
        tip.user_idx = CommonCode.USER_IDX.toLong()
        tip.category = selectedCategory
        tip.text = text.toString()
        tip.imgDir = selectedImage.value.toString()

        uiScope.launch {
            doInsert(tip)
        }

    }

    private suspend fun doInsert(tip: Tips) {
        withContext(Dispatchers.IO) {
            database.insert(tip)
            isDone.postValue(true)
        }
    }


    fun onImageSelected(data: Intent?) {
        selectedImage.value = data?.data!!
    }

    fun onCategorySelected(item:Int) {
        selectedCategory = categories.value!!.get(item).toString()
    }

}