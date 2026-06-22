package com.piumini.interntrack.network

data class PostResponse (
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)