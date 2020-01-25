package com.treewall.av.pizzaapp.data.authorization.dto

import com.google.gson.annotations.SerializedName

data class ResetPasswordResponseDTO(
    @SerializedName("message") val message: String
)