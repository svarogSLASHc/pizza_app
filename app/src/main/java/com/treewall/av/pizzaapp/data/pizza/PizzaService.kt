package com.treewall.av.pizzaapp.data.pizza

import com.treewall.av.pizzaapp.data.pizza.dto.PizzaDistributorResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface PizzaService {

    @GET("/pizza/machine/findbygeo")
    suspend fun getPizzaMachinesByGeo(@QueryMap query: Map<String, String>): Response<PizzaDistributorResponseDTO>
}