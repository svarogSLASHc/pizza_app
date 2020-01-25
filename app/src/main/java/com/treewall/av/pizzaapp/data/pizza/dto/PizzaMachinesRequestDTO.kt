package com.treewall.av.pizzaapp.data.pizza.dto

const val PARAM_LATITUDE = "lat"
const val PARAM_LONGITUDE = "lon"

data class PizzaDistributorRequestDTO(
    val latitude: Double,
    val longitude: Double
) {

    fun query() = mapOf(
        PARAM_LATITUDE to latitude.toString(),
        PARAM_LONGITUDE to longitude.toString()
    )
}