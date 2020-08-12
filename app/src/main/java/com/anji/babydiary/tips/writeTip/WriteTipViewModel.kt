package com.anji.babydiary.tips.writeTip

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.anji.babydiary.database.shopping.Tip
import com.anji.babydiary.database.shopping.TipDao
import kotlinx.coroutines.*

class WriteTipViewModel(val database:TipDao, application:Application) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    var selectedImage = MutableLiveData<Uri>()

    val categories = MutableLiveData<List<String>>()

    init {
        categories.value = listOf(
            "건강",
            "놀이",
            "교육",
            "기타"
        )
    }

    fun insertTip(text:CharSequence) {

        var tip = Tip()
        tip.category = "d"
        tip.text = text.toString()
        tip.imgDir = selectedImage.value.toString()

        uiScope.launch {
            doInsert(tip)
        }
    }

    private suspend fun doInsert(tip: Tip) {
        withContext(Dispatchers.IO) {
            database.insert(tip)
        }
    }

    fun onImageSelected(data: Intent?) {
        selectedImage.value = data?.data!!
    }

}