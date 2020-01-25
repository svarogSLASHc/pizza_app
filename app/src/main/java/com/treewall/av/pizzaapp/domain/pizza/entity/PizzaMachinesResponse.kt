package com.treewall.av.pizzaapp.domain.pizza.entity

data class PizzaMachinesResponse(
    val machines: List<PizzaMachineEntity>
)

data class PizzaMachineEntity(
    val id: String,
    val name: String,
    val code: String,
    val address: String,
    val latitude: String,
    val longitude: String,
    val createdAt: String,
    val updatedAt: String
)