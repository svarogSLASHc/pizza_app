package com.treewall.av.pizzaapp.domain.pizza.entity


data class ProductResponse(val products: List<ProductEntity>)

data class ProductEntity(
    val id: String,
    val price: String,
    val name: String,
    val img: String
)