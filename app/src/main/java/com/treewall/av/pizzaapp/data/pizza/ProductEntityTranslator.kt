package com.treewall.av.pizzaapp.data.pizza

import com.treewall.av.pizzaapp.data.pizza.dto.ProductDTO
import com.treewall.av.pizzaapp.data.pizza.dto.ProductRequestDTO
import com.treewall.av.pizzaapp.data.pizza.dto.ProductResponseDTO
import com.treewall.av.pizzaapp.domain.pizza.entity.ProductEntity
import com.treewall.av.pizzaapp.domain.pizza.entity.ProductRequest
import com.treewall.av.pizzaapp.domain.pizza.entity.ProductResponse

interface ProductEntityTranslator {
    suspend fun createProductRequestDTO(request: ProductRequest): ProductRequestDTO
    suspend fun createProductResponse(dto: ProductResponseDTO): ProductResponse
    suspend fun createProduct(dto: ProductDTO): ProductEntity
}

class ProductEntityTranslatorImpl : ProductEntityTranslator {
    override suspend fun createProductRequestDTO(request: ProductRequest) =
        with(request) { ProductRequestDTO(distributorId) }

    override suspend fun createProductResponse(dto: ProductResponseDTO) =
        ProductResponse(dto.products.map { createProduct(it) })

    override suspend fun createProduct(dto: ProductDTO) =
        with(dto) {
            ProductEntity(id, price, name, img)
        }
}
