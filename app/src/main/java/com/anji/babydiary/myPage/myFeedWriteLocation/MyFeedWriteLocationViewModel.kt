package com.anji.babydiary.myPage.myFeedWriteLocation

import android.util.Log
import androidx.lifecycle.ViewModel
import com.anji.babydiary.api.AddressApi
import com.anji.babydiary.api.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class MyFeedWriteLocationViewModel : ViewModel() {


    init {
        getAddressList()
    }

    fun getAddressList() {
        AddressApi.retrofitService.getProperties().enqueue(
            object: Callback<String>{
                override fun onFailure(call: Call<String>, t: Throwable) {


                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    response?.let {
                        Log.e("response","response ============================================================================================")
                        Log.e("response","${it.body()}")
                        Log.e("response","response ============================================================================================")
                    }
                }

            }
        )
    }

}