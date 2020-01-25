package com.treewall.av.pizzaapp.domain.pizza

import com.treewall.av.pizzaapp.data.Result
import com.treewall.av.pizzaapp.data.pizza.ProductRepository
import com.treewall.av.pizzaapp.domain.BaseUseCase
import com.treewall.av.pizzaapp.domain.pizza.entity.ProductRequest
import com.treewall.av.pizzaapp.domain.pizza.entity.ProductResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface GetProductsByDistributorIdUseCase : BaseUseCase<ProductRequest, Result<ProductResponse>>


class GetProductsByDistributorIdUseCaseImpl(
    private val repository: ProductRepository,
    private val dispatcher: CoroutineDispatcher
) : GetProductsByDistributorIdUseCase {
    override suspend fun invoke(parameters: ProductRequest) = withContext(dispatcher) {
        return@withContext repository.getProductByDistributorId(parameters)
    }
}