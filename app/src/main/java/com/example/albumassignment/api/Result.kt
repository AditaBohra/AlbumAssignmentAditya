package com.example.albumassignment.api

import retrofit2.Response

sealed class Result {
    data class Success<T>(val data: T) : Result()
    data class Error(val error: String) : Result()
}