package com.treewall.av.pizzaapp.domain.authorization.entity


data class CustomerEntity(
    val id: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val addresses: List<AddressEntity>
)

data class AddressEntity(
    val defaultBilling: Boolean,
    val firstName: String,
    val city: String,
    val street: List<String>,
    val postcode: String,
    val telephone: String,
    val defaultShipping: Boolean,
    val region: RegionEntity,
    val countryId: String,
    val lastName: String
)

data class RegionEntity(
    val regionCode: String,
    val regionId: Int,
    val region: String
)