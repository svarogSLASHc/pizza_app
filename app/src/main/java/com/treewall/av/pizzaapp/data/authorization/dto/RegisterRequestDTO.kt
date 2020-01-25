package com.treewall.av.pizzaapp.data.authorization.dto

import com.google.gson.annotations.SerializedName

data class RegisterRequestDTO(
    @SerializedName("customer") val createCustomerDTO: CreateCustomerDTO,
    @SerializedName("password") val password: String
)

data class CreateCustomerDTO(
    @SerializedName("firstname") val firstName: String,
    @SerializedName("lastname") val lastName: String,
    @SerializedName("email") val email: String
)