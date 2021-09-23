package com.example.submission3.data


data class DetailsResponse(
    val login: String,
    val company: String,
    val location: String,
    val avatar_url: String,
    val public_repos: Int,
    val name: String,
    val followers: Int,
    val following: Int
)