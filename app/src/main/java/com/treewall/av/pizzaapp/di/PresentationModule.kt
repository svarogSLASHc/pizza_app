package com.treewall.av.pizzaapp.di

import android.content.Context
import com.treewall.av.pizzaapp.MainActivityViewModel
import com.treewall.av.pizzaapp.domain.authorization.logger.BaseLogger
import com.treewall.av.pizzaapp.presentation.distributor.SelectedDistributorViewModel
import com.treewall.av.pizzaapp.presentation.forgot_password.ForgotPasswordViewModel
import com.treewall.av.pizzaapp.presentation.login.LoginViewModel
import com.treewall.av.pizzaapp.presentation.map.MapViewModel
import com.treewall.av.pizzaapp.presentation.product_list.ProductListViewModel
import com.treewall.av.pizzaapp.presentation.register.RegisterViewModel
import com.treewall.av.pizzaapp.presentation.splash.SplashViewModel
import com.treewall.av.pizzaapp.presentation.welcome.WelcomeViewModel
import com.treewall.av.pizzaapp.utils.GetLocationManager
import com.treewall.av.pizzaapp.utils.GetLocationManagerImpl
import com.treewall.av.pizzaapp.utils.UserCredentialsValidator
import com.treewall.av.pizzaapp.utils.UserCredentialsValidatorImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { MainActivityViewModel() }
    factory { UserCredentialsValidatorImpl() as UserCredentialsValidator }
    viewModel { RegisterViewModel(get(), get()) }
    viewModel { WelcomeViewModel() }
    viewModel { SplashViewModel(get(), get()) }
    viewModel { LoginViewModel(get(), get(), get()) }
    viewModel { ForgotPasswordViewModel(get(), get()) }
    factory { provideGetLocationManager(androidContext(), get()) }
    viewModel { MapViewModel(get(), get(), get(), get()) }
    viewModel { SelectedDistributorViewModel(get()) }
    viewModel { ProductListViewModel(get(), get()) }
}

fun provideGetLocationManager(context: Context, logger: BaseLogger): GetLocationManager {
    return GetLocationManagerImpl(context, logger)
}
