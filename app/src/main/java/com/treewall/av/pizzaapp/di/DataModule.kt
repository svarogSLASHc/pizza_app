package com.treewall.av.pizzaapp.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.treewall.av.pizzaapp.data.authorization.*
import com.treewall.av.pizzaapp.data.logger.TimberLogger
import com.treewall.av.pizzaapp.data.pizza.*
import com.treewall.av.pizzaapp.data.shared.SelectedDistributorDataSource
import com.treewall.av.pizzaapp.data.shared.SelectedDistributorDataSourceImpl
import com.treewall.av.pizzaapp.domain.authorization.logger.BaseLogger
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module


val dataModule = module {
    single { getBaseLogger() }
    single { Dispatchers.IO }
    single { getAuthEntityTranslator(get()) }
    single { getRemoteAuthDataSource(get(), get(), get()) }
    single { getAuthRepository(get()) }
    single { getSharedPreferences(get()) }
    factory { getTokenDataStore(get()) }
    single { getTokenRepository(get()) }
    single { getPizzaEntityTranslator() }
    single { getRemotePizzaDataSource(get(), get(), get()) }
    single { getPizzaRepository(get(), get()) }
    factory { getDistributorDataSource(get()) }
    single { getDistributorRepository(get()) }
    factory { getSelectedDistributorDataSource() }
    factory { getProductEntityTranslator() }
    factory { getRemoteProductDataSource(get(), get(), get()) }
    single { getProductRepository(get()) }
}

fun getAuthEntityTranslator(
    logger: BaseLogger
): AuthEntityTranslator {
    return AuthEntityTranslatorImpl(logger)
}

fun getRemoteAuthDataSource(
    service: AuthorizationService,
    entityTranslator: AuthEntityTranslator,
    gson: Gson
): RemoteAuthDataSource {
    return RemoteAuthDataSourceImpl(service, entityTranslator, gson)
}

fun getAuthRepository(
    remoteDataSource: RemoteAuthDataSource
): AuthorizationRepository {
    return AuthorizationRepositoryImpl(remoteDataSource)
}

fun getBaseLogger() = TimberLogger() as BaseLogger

fun getSharedPreferences(appContext: Application): SharedPreferences {
    return appContext.getSharedPreferences("STORAGE_NAME", Context.MODE_PRIVATE)
}

fun getTokenDataStore(
    sharedPreferences: SharedPreferences
) = TokenDataStoreImpl(sharedPreferences) as TokenDataStore

fun getTokenRepository(
    tokenDataStore: TokenDataStore
) = TokenRepositoryImpl(tokenDataStore) as TokenRepository

fun getPizzaEntityTranslator() = PizzaEntityTranslatorImpl() as PizzaEntityTranslator

fun getPizzaRepository(
    remoteDataSource: RemotePizzaDataSource,
    memoryDataSource: SelectedDistributorDataSource
): PizzaDistributorRepository {
    return PizzaRepositoryImpl(remoteDataSource, memoryDataSource)
}

fun getRemotePizzaDataSource(
    service: PizzaService, translator: PizzaEntityTranslator, converter: Gson
): RemotePizzaDataSource {
    return RemotePizzaDataSourceImpl(service, translator, converter)
}

fun getDistributorDataSource(
    context: Context
) = DistributorAddressDataSourceImpl(context) as DistributorAddressDataSource

fun getDistributorRepository(
    dataSource: DistributorAddressDataSource
) = DistributorAddressRepositoryImpl(dataSource) as DistributorAddressRepository

fun getSelectedDistributorDataSource() =
    SelectedDistributorDataSourceImpl() as SelectedDistributorDataSource

fun getProductEntityTranslator() = ProductEntityTranslatorImpl() as ProductEntityTranslator

fun getRemoteProductDataSource(
    service: ProductService, translator: ProductEntityTranslator,
    converter: Gson
) = RemoteProductDataSourceImpl(service, translator, converter) as RemoteProductDataSource

fun getProductRepository(
    remoteDataSource: RemoteProductDataSource
) = ProductRepositoryImpl(remoteDataSource) as ProductRepository
