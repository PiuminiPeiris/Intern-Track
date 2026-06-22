package com.piumini.interntrack.network

import com.google.gson.annotations.SerializedName

data class GitHubRepositoryResponse(
    val name: String,
    @SerializedName("full_name")
    val fullName: String,
    val description: String?,
    @SerializedName("stargazers_count")
    val stars: Int,
    val language: String?
)
