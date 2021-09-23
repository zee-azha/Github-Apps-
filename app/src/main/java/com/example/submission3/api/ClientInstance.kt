package com.example.submission3.api


import com.example.submission3.util.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ClientInstance {


   private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

   val apiInstances: Api = retrofit.create(Api::class.java)
}