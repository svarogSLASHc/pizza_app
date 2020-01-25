package com.treewall.av.pizzaapp.domain.authorization.entity


data class RegisterRequestEntity(
    val createCustomerEntity: CreateCustomerEntity,
    val password: String
) {
    constructor(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ) : this(CreateCustomerEntity(firstName, lastName, email), password)
}

data class CreateCustomerEntity(
    val firstName: String,
    val lastName: String,
    val email: String
)