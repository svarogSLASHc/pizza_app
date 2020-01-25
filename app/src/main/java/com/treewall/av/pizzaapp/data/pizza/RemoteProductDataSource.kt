package com.treewall.av.pizzaapp.data.pizza

import com.google.gson.Gson
import com.treewall.av.pizzaapp.data.Result
import com.treewall.av.pizzaapp.data.base.BaseRemoteDataSource
import com.treewall.av.pizzaapp.domain.pizza.entity.ProductRequest
import com.treewall.av.pizzaapp.domain.pizza.entity.ProductResponse

interface RemoteProductDataSource {
    suspend fun getProductByDistributorId(request: ProductRequest): Result<ProductResponse>
}

class RemoteProductDataSourceImpl(
    private val service: ProductService,
    private val translator: ProductEntityTranslator,
    gsonConverter: Gson
) : BaseRemoteDataSource(gsonConverter), RemoteProductDataSource {
    override suspend fun getProductByDistributorId(request: ProductRequest): Result<ProductResponse> {
        val requestDTO = translator.createProductRequestDTO(request)
        return processResponse(
            { service.getProductsByDistributorId(requestDTO.query()) },
            translator::createProductResponse
        )
    }
}