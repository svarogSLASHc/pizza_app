package com.treewall.av.pizzaapp.data.authorization.dto

import com.google.gson.annotations.SerializedName

data class CustomerDTO(
    @SerializedName("id") val id: String,
    @SerializedName("group_id") val groupId: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("created_in") val createdIn: String,
    @SerializedName("email") val email: String,
    @SerializedName("firstname") val firstName: String,
    @SerializedName("lastname") val lastName: String,
    @SerializedName("store_id") val storeId: String,
    @SerializedName("website_id") val websiteId: String,
    @SerializedName("addresses") val addresses: List<AddressDTO>,
    @SerializedName("disable_auto_group_change") val disableAutoGroupChange: Int,
    @SerializedName("extension_attributes") val extensionAttributes: ExtensionAttributesDTO
)

data class ExtensionAttributesDTO(
    @SerializedName("is_subscribed") val isSubscribed: Boolean
)

data class AddressDTO(
    @SerializedName("defaultBilling") val defaultBilling: Boolean,
    @SerializedName("firstname") val firstName: String,
    @SerializedName("city") val city: String,
    @SerializedName("street") val street: List<String>,
    @SerializedName("postcode") val postcode: String,
    @SerializedName("telephone") val telephone: String,
    @SerializedName("defaultShipping") val defaultShipping: Boolean,
    @SerializedName("region") val region: RegionDTO,
    @SerializedName("countryId") val countryId: String,
    @SerializedName("lastname") val lastName: String
)

data class RegionDTO(
    @SerializedName("regionCode") val regionCode: String,
    @SerializedName("regionId") val regionId: Int,
    @SerializedName("region") val region: String
)



