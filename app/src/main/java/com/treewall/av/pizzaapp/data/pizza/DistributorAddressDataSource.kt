package com.treewall.av.pizzaapp.data.pizza

import android.content.Context
import android.location.Geocoder
import com.treewall.av.pizzaapp.data.Result
import com.treewall.av.pizzaapp.domain.pizza.entity.DistributorAddress
import com.treewall.av.pizzaapp.domain.pizza.entity.PizzaRequest
import java.util.*


interface DistributorAddressDataSource {
    suspend fun getPizzaMachinesAddress(request: PizzaRequest): Result<DistributorAddress?>
}

class DistributorAddressDataSourceImpl(
    private val context: Context
) : DistributorAddressDataSource {

    override suspend fun getPizzaMachinesAddress(request: PizzaRequest): Result<DistributorAddress?> {
        val address = Geocoder(context, Locale.getDefault())
            .getFromLocation(
                request.latitude,
                request.longitude,
                // In this sample, we get just a single address.
                1
            )
        var result: DistributorAddress? = null
        if (address.isNotEmpty()) {
            result = DistributorAddress(with(address[0]) {
                (0..maxAddressLineIndex).map { getAddressLine(it) }.toString()
            })
        }
        return Result.Success(result)
    }
}