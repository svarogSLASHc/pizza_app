package com.treewall.av.pizzaapp.data.pizza

import com.treewall.av.pizzaapp.data.pizza.dto.ProductResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ProductService {

    @GET("/pizza/catalog/index")
    suspend fun getProductsByDistributorId(@QueryMap query: Map<String, String>): Response<ProductResponseDTO>
}