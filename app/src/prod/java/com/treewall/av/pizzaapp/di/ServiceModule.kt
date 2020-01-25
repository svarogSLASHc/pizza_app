package com.treewall.av.pizzaapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.treewall.av.pizzaapp.data.authorization.AuthorizationService
import com.treewall.av.pizzaapp.data.pizza.PizzaService
import com.treewall.av.pizzaapp.data.pizza.ProductService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val BASE_URL = "http://xxxxx.test"

val serviceModule = module {
    single { getLoggingInterceptor() }
    single { getOkHttpClient(get()) }
    single { getGson() }
    single { getRetrofit(get(), get()) }
    single { getAuthService(get()) }
    single { getPizzaService(get()) }
    factory { getProductService(get()) }
}

fun getLoggingInterceptor(): Interceptor {
    return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
}

fun getOkHttpClient(loggingInterceptor: Interceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
        .build()
}

fun getGson(): Gson {
    return GsonBuilder().create()
}

fun getRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
    return Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(BASE_URL)
        .build()
}

fun getAuthService(retrofit: Retrofit): AuthorizationService {
    return retrofit.create(AuthorizationService::class.java)
}

fun getPizzaService(retrofit: Retrofit): PizzaService {
    return retrofit.create(PizzaService::class.java)
}


fun getProductService(retrofit: Retrofit): ProductService {
    return retrofit.create(ProductService::class.java)
}

