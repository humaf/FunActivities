package com.design.myapplication.network

import com.design.myapplication.model.Item
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


const val BASE_URL = "http://www.boredapi.com"

interface MyApi {


    @GET("/api/activity/")
    fun getItems() : retrofit2.Call<Item>

    companion object {
        operator fun invoke() : MyApi{
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }
}