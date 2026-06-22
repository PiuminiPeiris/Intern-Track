package com.piumini.interntrack.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PostRetrofitClient {
    val apiService: PostApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostApiService::class.java)
    }
}