package com.treewall.av.pizzaapp.data.pizza

import com.treewall.av.pizzaapp.data.Result
import com.treewall.av.pizzaapp.domain.pizza.entity.DistributorAddress
import com.treewall.av.pizzaapp.domain.pizza.entity.PizzaRequest


interface DistributorAddressRepository {
    suspend fun getPizzaMachinesAddress(pizzaRequest: PizzaRequest): Result<DistributorAddress?>
}

class DistributorAddressRepositoryImpl(
    private val dataSource: DistributorAddressDataSource
) : DistributorAddressRepository {

    override suspend fun getPizzaMachinesAddress(pizzaRequest: PizzaRequest) =
        dataSource.getPizzaMachinesAddress(pizzaRequest)
}