package com.piumini.interntrack.network

import retrofit2.http.GET
import retrofit2.http.Query

interface PostApiService{
    @GET("posts")
    suspend fun getPosts(
       @Query("_page") page: Int,
       @Query("_limit") limit: Int
    ): List<PostResponse>
}