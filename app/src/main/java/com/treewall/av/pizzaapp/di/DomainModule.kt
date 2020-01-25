package com.treewall.av.pizzaapp.di

import com.treewall.av.pizzaapp.domain.authorization.*
import com.treewall.av.pizzaapp.domain.pizza.GetPizzaMachinesByGeoUseCase
import com.treewall.av.pizzaapp.domain.pizza.GetPizzaMachinesByGeoUseCaseImpl
import com.treewall.av.pizzaapp.domain.pizza.GetProductsByDistributorIdUseCase
import com.treewall.av.pizzaapp.domain.pizza.GetProductsByDistributorIdUseCaseImpl
import org.koin.dsl.module

val domainModule = module {
    factory { RegisterUseCaseImpl(get(), get()) as RegisterUseCase }

    factory { LoginUseCaseImpl(get(), get(), get()) as LoginUseCase }

    factory { GetTokenUseCaseImpl(get(), get()) as GetTokenUseCase }

    factory { SaveTokenUseCaseImpl(get(), get()) as SaveTokenUseCase }

    factory { ResetPasswordUseCaseImpl(get(), get()) as ResetPasswordUseCase }

    factory { GetPizzaMachinesByGeoUseCaseImpl(get(), get()) as GetPizzaMachinesByGeoUseCase }

    factory { GetSelectedDistributorImpl(get(), get()) as GetSelectedDistributor }

    factory { SaveSelectedDistributorImpl(get()) as SaveSelectedDistributor }

    factory {
        GetProductsByDistributorIdUseCaseImpl(
            get(),
            get()
        ) as GetProductsByDistributorIdUseCase
    }
}