package com.treewall.av.pizzaapp.data.pizza

import com.treewall.av.pizzaapp.data.Result
import com.treewall.av.pizzaapp.data.shared.SelectedDistributorDataSource
import com.treewall.av.pizzaapp.domain.SelectedDistributor
import com.treewall.av.pizzaapp.domain.pizza.entity.PizzaMachinesResponse
import com.treewall.av.pizzaapp.domain.pizza.entity.PizzaRequest

interface PizzaDistributorRepository {
    suspend fun getPizzaMachinesByGeo(pizzaRequest: PizzaRequest): Result<PizzaMachinesResponse>

    fun saveSelected(distributor: SelectedDistributor)

    fun getSelected(): Result<SelectedDistributor>
}

class PizzaRepositoryImpl(
    private val remoteDataSource: RemotePizzaDataSource,
    private val memoryDataSource: SelectedDistributorDataSource
) : PizzaDistributorRepository {

    override suspend fun getPizzaMachinesByGeo(pizzaRequest: PizzaRequest) =
        remoteDataSource.getPizzaMachinesByGeo(pizzaRequest)

    override fun saveSelected(distributor: SelectedDistributor) {
        memoryDataSource.save(distributor)
    }

    override fun getSelected() = memoryDataSource.get()
}