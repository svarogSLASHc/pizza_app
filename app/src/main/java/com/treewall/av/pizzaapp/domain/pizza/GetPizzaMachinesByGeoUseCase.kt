package com.treewall.av.pizzaapp.domain.pizza

import com.treewall.av.pizzaapp.data.Result
import com.treewall.av.pizzaapp.data.pizza.PizzaDistributorRepository
import com.treewall.av.pizzaapp.domain.BaseUseCase
import com.treewall.av.pizzaapp.domain.pizza.entity.PizzaMachinesResponse
import com.treewall.av.pizzaapp.domain.pizza.entity.PizzaRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface GetPizzaMachinesByGeoUseCase : BaseUseCase<PizzaRequest, Result<PizzaMachinesResponse>>

class GetPizzaMachinesByGeoUseCaseImpl(
    private val distributorRepository: PizzaDistributorRepository,
    private val dispatcher: CoroutineDispatcher
) : GetPizzaMachinesByGeoUseCase {
    override suspend fun invoke(parameters: PizzaRequest) = withContext(dispatcher) {
        return@withContext distributorRepository.getPizzaMachinesByGeo(parameters)
    }
}