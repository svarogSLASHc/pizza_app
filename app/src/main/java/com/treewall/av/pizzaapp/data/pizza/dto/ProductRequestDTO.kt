package com.treewall.av.pizzaapp.data.pizza.dto

const val PARAM_MACHINE = "machine"

data class ProductRequestDTO(
    val id: Int
) {

    fun query() = mapOf(
        PARAM_MACHINE to id.toString()
    )
}