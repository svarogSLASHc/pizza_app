package com.treewall.av.pizzaapp.domain.authorization.entity

data class LoginRequestEntity(
    val userName: String,
    val password: String
)