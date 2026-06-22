package com.piumini.interntrack.network

import retrofit2.http.GET

interface GitHubApiService{
    @GET("repos/square/retrofit")
    suspend fun getRetrofitRepository(): GitHubRepositoryResponse
}
