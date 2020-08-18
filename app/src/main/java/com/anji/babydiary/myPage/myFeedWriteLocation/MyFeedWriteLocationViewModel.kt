package com.anji.babydiary.myPage.myFeedWriteLocation

import android.util.Log
import androidx.lifecycle.ViewModel
import com.anji.babydiary.api.AddressApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MyFeedWriteLocationViewModel : ViewModel() {

    val job = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + job)

    init {

    }

    fun getAddressList(searchText:CharSequence) {

        Log.e("search","search text: ${searchText}")

        uiScope.launch {
            try {
                val addressResult = AddressApi.retrofitService.getProperties()
                Log.e("response","========================================================")
                Log.e("response","${addressResult}")
                Log.e("response","========================================================")

            }catch (e:Exception) {
                Log.e("response","========================================================")
                Log.e("response","${e.printStackTrace()}")
                Log.e("response","========================================================")
            }
        }

    }

    fun onItemAddressClicked() {

    }

}