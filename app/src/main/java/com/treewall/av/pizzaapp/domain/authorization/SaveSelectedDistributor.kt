package com.treewall.av.pizzaapp.domain.authorization

import com.treewall.av.pizzaapp.data.pizza.PizzaDistributorRepository
import com.treewall.av.pizzaapp.domain.BaseUseCase
import com.treewall.av.pizzaapp.domain.SelectedDistributor

interface SaveSelectedDistributor : BaseUseCase<SelectedDistributor, Unit>

class SaveSelectedDistributorImpl(
    private val distributorRepository: PizzaDistributorRepository
) : SaveSelectedDistributor {
    override suspend fun invoke(parameters: SelectedDistributor) {
        distributorRepository.saveSelected(parameters)
    }
}