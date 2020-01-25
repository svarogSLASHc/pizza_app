package com.treewall.av.pizzaapp.domain.authorization

import com.treewall.av.pizzaapp.data.Result
import com.treewall.av.pizzaapp.data.pizza.PizzaDistributorRepository
import com.treewall.av.pizzaapp.domain.BaseUseCase
import com.treewall.av.pizzaapp.domain.SelectedDistributor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface GetSelectedDistributor : BaseUseCase<Unit, Result<SelectedDistributor>> {
    suspend operator fun invoke(): Result<SelectedDistributor>
}

class GetSelectedDistributorImpl(
    private val distributorRepository: PizzaDistributorRepository,
    private val ioDispatcher: CoroutineDispatcher
) : GetSelectedDistributor {
    override suspend fun invoke() = invoke(Unit)

    override suspend fun invoke(parameters: Unit) = withContext(ioDispatcher) {
        distributorRepository.getSelected()
    }
}