package com.treewall.av.pizzaapp.data.pizza.dto

import com.google.gson.annotations.SerializedName

data class ProductResponseDTO(
    @SerializedName("products") val products: List<ProductDTO>
)

data class ProductDTO(
    @SerializedName("id") val id: String,
    @SerializedName("price") val price: String,
    @SerializedName("name") val name: String,
    @SerializedName("img") val img: String
)