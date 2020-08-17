package com.anji.babydiary.api

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET


private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com"
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface ApiService {
    @GET("realestate")
    fun getProperties():
            Call<String>
}

object AddressApi {
    val retrofitService:ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}