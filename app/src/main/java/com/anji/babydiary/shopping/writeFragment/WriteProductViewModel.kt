package com.anji.babydiary.shopping.writeFragment

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.anji.babydiary.database.shopping.Shopping
import com.anji.babydiary.database.shopping.ShoppingDao
import kotlinx.coroutines.*

class WriteProductViewModel(val database:ShoppingDao, application: Application) : AndroidViewModel(application) {

    var selectedImage = MutableLiveData<Uri>()
    var productTitle = MutableLiveData<String>()
    var productPrice = MutableLiveData<String>()
    var productURL = MutableLiveData<String>()
    var isValid= MutableLiveData<Boolean>()
    var invalidString = ""

    var isDone = MutableLiveData<Boolean>()

    var isProductURLTag = MutableLiveData<Int>()

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    init {
        isValid.value = true
        isProductURLTag.value = View.GONE
        isDone.value = false
    }

    fun onImageSelected(data:Intent?) {
        selectedImage.value = data?.data!!
    }

    fun saveData(title:CharSequence, price:CharSequence, url:CharSequence) {

        if (validationCheck(title, price, url).value == true) {
            var shopData = Shopping()
            shopData.title = title.toString()
            shopData.url = url.toString()
            shopData.price = price.toString()
            shopData.imgDir = selectedImage.value!!.toString()

            uiScope.launch {
                insertData(shopData)
                isDone.value = true

            }
        }
    }

    fun setURLTag(url:CharSequence) {

        if (url.toString().equals("")) {
            isProductURLTag.value = View.GONE
        }else {
            isProductURLTag.value = View.VISIBLE
        }

    }

    private fun validationCheck(title:CharSequence, price:CharSequence, url:CharSequence):MutableLiveData<Boolean> {

        if (title.toString().equals("")) {
            isValid.value = false
            invalidString = "제목"
        }
        if (price.toString().equals("")) {
            isValid.value = false
            invalidString = "가격"
        }
        if (url.toString().equals("")) {
            isValid.value = false
            invalidString = "URL"
        }
        if (selectedImage.value == null) {
            isValid.value = false
            invalidString = "이미지"
        }

        return isValid
    }

    private suspend fun insertData(shopdata:Shopping) {
        withContext(Dispatchers.IO) {
            database.insert(shopdata)

        }
    }


}


