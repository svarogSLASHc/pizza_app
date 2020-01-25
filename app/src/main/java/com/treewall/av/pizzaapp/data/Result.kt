package com.treewall.av.pizzaapp.data

import com.google.gson.annotations.SerializedName

sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()

    data class Error<out T>(val code: Int?, val response: ErrorResponse?, val data: T? = null) :
        Result<T>()

    object NetworkError : Result<Nothing>()

    object Empty : Result<Nothing>()

    data class Loading<out T>(val data: T?) : Result<T>()
}

data class ErrorResponse(
    @SerializedName("message") val message: String?,
    @SerializedName("parameters") val parameters: String?
)