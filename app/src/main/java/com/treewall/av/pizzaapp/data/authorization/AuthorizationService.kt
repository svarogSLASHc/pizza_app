package com.treewall.av.pizzaapp.data.authorization

import com.treewall.av.pizzaapp.data.authorization.dto.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthorizationService {

    @POST("/rest/V1/integration/customer/token")
    suspend fun login(@Body request: LoginRequestDTO): Response<String>

    @POST("/rest/V1/customers/")
    suspend fun registerCustomer(@Body request: RegisterRequestDTO): Response<CustomerDTO>

    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/pizza/customer/rsesetpassword")
    suspend fun resetPassword(@Body request: ResetPasswordRequestDTO): Response<ResetPasswordResponseDTO>
}