package com.treewall.av.pizzaapp.data.base

import com.google.gson.Gson
import com.treewall.av.pizzaapp.data.ErrorResponse
import com.treewall.av.pizzaapp.data.Result
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class BaseRemoteDataSource(
    private val gsonConverter: Gson
) {

    protected suspend fun <Dto, Entity> processResponse(
        call: suspend () -> Response<Dto>,
        mapResponse: suspend (Dto) -> Entity
    ): Result<Entity> {
        return try {
            val response = call()
            val body = response.body()
            if (!response.isSuccessful) {
                Result.Error(response.code(), parseError(response))
            } else if (body == null) {
                Result.Error(response.code(), null, null)
            } else {
                Result.Success(mapResponse(body))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            when (e) {
                is IOException -> Result.NetworkError
                is HttpException -> {
                    Result.Error(e.code(), parseError(e.response()))
                }
                else -> Result.Error(null, null)
            }
        }
    }

    private fun <T> parseError(response: Response<T>?): ErrorResponse? {
        return response?.errorBody()?.string()?.let {
            gsonConverter.fromJson(it, ErrorResponse::class.java)
        }
    }
}