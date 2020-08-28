package com.anji.babydiary.tips.writeTip

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.anji.babydiary.common.CommonCode
import com.anji.babydiary.database.shopping.Tip
import com.anji.babydiary.database.shopping.TipDao
import kotlinx.coroutines.*

class WriteTipViewModel(val database:TipDao, application:Application) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    var selectedImage = MutableLiveData<Uri>()

    var isDone = MutableLiveData<Boolean>()

    val categories = MutableLiveData<Array<String>>()
    var selectedCategory = ""
    init {
        isDone.value = false
        categories.value = arrayOf(
            "건강",
            "놀이",
            "교육",
            "기타"
        )
    }

    fun insertTip(text:CharSequence) {


        var tip = Tip()
        tip.user_idx = CommonCode.USER_IDX
        tip.category = selectedCategory
        tip.text = text.toString()
        tip.imgDir = selectedImage.value.toString()

        uiScope.launch {
            doInsert(tip)
            isDone.value = true
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

    fun onCategorySelected(item:Int) {
        selectedCategory = categories.value!!.get(item).toString()
    }

}