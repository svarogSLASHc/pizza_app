package com.treewall.av.pizzaapp.data.pizza

import com.google.gson.Gson
import com.treewall.av.pizzaapp.data.Result
import com.treewall.av.pizzaapp.data.base.BaseRemoteDataSource
import com.treewall.av.pizzaapp.domain.pizza.entity.PizzaMachinesResponse
import com.treewall.av.pizzaapp.domain.pizza.entity.PizzaRequest

interface RemotePizzaDataSource {
    suspend fun getPizzaMachinesByGeo(request: PizzaRequest): Result<PizzaMachinesResponse>
}

class RemotePizzaDataSourceImpl(
    private val service: PizzaService,
    private val translator: PizzaEntityTranslator,
    gsonConverter: Gson
) : BaseRemoteDataSource(gsonConverter), RemotePizzaDataSource {

    override suspend fun getPizzaMachinesByGeo(request: PizzaRequest): Result<PizzaMachinesResponse> {
        val pizzaRequestDTO = translator.createPizzaRequestDTO(request)
        return processResponse(
            { service.getPizzaMachinesByGeo(pizzaRequestDTO.query()) },
            translator::createPizzaResponse
        )
    }
}