package com.treewall.av.pizzaapp.data.authorization.dto

import com.google.gson.annotations.SerializedName

data class ResetPasswordRequestDTO(
    @SerializedName("username") val username: String
)