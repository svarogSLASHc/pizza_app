package com.treewall.av.pizzaapp.domain

interface BaseUseCase<in Parameters, out Result> {
    suspend operator fun invoke(parameters: Parameters): Result
}
