package com.treewall.av.pizzaapp.data.authorization.dto

import com.google.gson.annotations.SerializedName

class LoginRequestDTO(
    @SerializedName("username") val userName: String,
    @SerializedName("password") val password: String
)