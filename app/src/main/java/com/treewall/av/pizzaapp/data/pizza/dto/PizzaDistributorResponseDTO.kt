package com.treewall.av.pizzaapp.data.pizza.dto

import com.google.gson.annotations.SerializedName

data class PizzaDistributorResponseDTO(
    @SerializedName("machines") val machines: List<PizzaDistributorDTO>
)

data class PizzaDistributorDTO(
    @SerializedName("machine_id") val id: String,
    @SerializedName("nom") val name: String,
    @SerializedName("code") val code: String,
    @SerializedName("latitude") val latitude: String,
    @SerializedName("longitude") val longitude: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("address") val address: String
)