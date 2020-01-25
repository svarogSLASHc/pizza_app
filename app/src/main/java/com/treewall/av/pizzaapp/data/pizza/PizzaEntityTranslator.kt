package com.treewall.av.pizzaapp.data.pizza

import com.treewall.av.pizzaapp.data.pizza.dto.PizzaDistributorDTO
import com.treewall.av.pizzaapp.data.pizza.dto.PizzaDistributorRequestDTO
import com.treewall.av.pizzaapp.data.pizza.dto.PizzaDistributorResponseDTO
import com.treewall.av.pizzaapp.domain.pizza.entity.PizzaMachineEntity
import com.treewall.av.pizzaapp.domain.pizza.entity.PizzaMachinesResponse
import com.treewall.av.pizzaapp.domain.pizza.entity.PizzaRequest

interface PizzaEntityTranslator {
    suspend fun createPizzaRequestDTO(request: PizzaRequest): PizzaDistributorRequestDTO
    suspend fun createPizzaResponse(dto: PizzaDistributorResponseDTO): PizzaMachinesResponse
    suspend fun createPizzaMachine(dto: PizzaDistributorDTO): PizzaMachineEntity
}

class PizzaEntityTranslatorImpl : PizzaEntityTranslator {
    override suspend fun createPizzaRequestDTO(request: PizzaRequest) =
        with(request) {
            PizzaDistributorRequestDTO(latitude, longitude)
        }

    override suspend fun createPizzaResponse(dto: PizzaDistributorResponseDTO) =
        PizzaMachinesResponse(dto.machines.map { createPizzaMachine(it) })

    override suspend fun createPizzaMachine(dto: PizzaDistributorDTO) =
        with(dto) {
            PizzaMachineEntity(id, name, code, address, latitude, longitude, createdAt, updatedAt)
        }
}
