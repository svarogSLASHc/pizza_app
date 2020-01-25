package com.treewall.av.pizzaapp.data.pizza

import com.treewall.av.pizzaapp.data.Result
import com.treewall.av.pizzaapp.domain.pizza.entity.ProductRequest
import com.treewall.av.pizzaapp.domain.pizza.entity.ProductResponse

interface ProductRepository {
    suspend fun getProductByDistributorId(request: ProductRequest): Result<ProductResponse>
}

class ProductRepositoryImpl(
    private val remoteDataSource: RemoteProductDataSource
) : ProductRepository {
    override suspend fun getProductByDistributorId(request: ProductRequest) =
        remoteDataSource.getProductByDistributorId(request)
}