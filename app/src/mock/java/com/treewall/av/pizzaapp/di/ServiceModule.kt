package com.treewall.av.pizzaapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.treewall.av.pizzaapp.data.authorization.AuthorizationService
import com.treewall.av.pizzaapp.data.authorization.dto.*
import com.treewall.av.pizzaapp.data.pizza.PizzaService
import com.treewall.av.pizzaapp.data.pizza.ProductService
import com.treewall.av.pizzaapp.data.pizza.dto.PizzaDistributorResponseDTO
import com.treewall.av.pizzaapp.data.pizza.dto.ProductResponseDTO
import org.koin.dsl.module
import retrofit2.Response


val serviceModule = module {
    single { getGson() }
    single { getPizzaService(get()) }
    single { getAuthService(get()) }
    factory { getProductService(get()) }
}

fun getGson(): Gson {
    return GsonBuilder().create()
}

fun getAuthService(gson: Gson) = object : AuthorizationService {
    override suspend fun login(request: LoginRequestDTO): Response<String> {
        return Response.success("z8vcvk8p28ack08zvxbpf0ap6gcz06c8")
    }

    override suspend fun registerCustomer(request: RegisterRequestDTO): Response<CustomerDTO> {
        return Response.success(null)
    }

    override suspend fun resetPassword(request: ResetPasswordRequestDTO): Response<ResetPasswordResponseDTO> {
        return Response.success(null)
    }
}

fun getPizzaService(gson: Gson) = object : PizzaService {
    override suspend fun getPizzaMachinesByGeo(query: Map<String, String>): Response<PizzaDistributorResponseDTO> {
        return Response.success(
            gson.fromJson(
                pizzaMachinesResponseJson, PizzaDistributorResponseDTO::class.java
            )
        )
    }

}

fun getProductService(gson: Gson) = object : ProductService {
    override suspend fun getProductsByDistributorId(query: Map<String, String>): Response<ProductResponseDTO> {
        return Response.success(
            gson.fromJson(
                productsByDistributorResponseJson, ProductResponseDTO::class.java
            )
        )
    }
}

private val pizzaMachinesResponseJson = """
                    {
  "machines": [
    {
      "machine_id": "1",
      "nom": "test machien",
      "code": "Lion",
      "latitude": "45.748464",
      "longitude": "4.84671",
      "created_at": "2019-11-26 11:28:41",
      "updated_at": "2019-11-26 11:36:34",
      "address": "Lore impusm 24 street"
    },
    {
      "machine_id": "2",
      "nom": "Crazy Rocky",
      "code": "07",
      "latitude": "48.6173014",
      "longitude": "22.293844",
      "created_at": "2019-12-03 10:45:00",
      "updated_at": "2019-12-03 10:45:00",
      "address": "Lore impusm 24 street"
    },
    {
      "machine_id": "3",
      "nom": "\u0421hili+Pizza",
      "code": "07",
      "latitude": "48.6249844",
      "longitude": "22.2981233",
      "created_at": "2019-12-03 11:51:33",
      "updated_at": "2019-12-03 11:51:33",
      "address": "Lore impusm 24 street"
    },
    {
      "machine_id": "4",
      "nom": "Le+Sud",
      "code": "Lion",
      "latitude": "45.7563614",
      "longitude": "4.8333622",
      "created_at": "2019-12-03 11:53:50",
      "updated_at": "2019-12-03 11:53:50",
      "address": "Lore impusm 24 street"
    }
  ]
}""".trimIndent()

private val productsByDistributorResponseJson = """
   {
  "products": [
    {
      "id": 1,
      "price": 7.99,
      "name": "Orientale",
      "img": "http:\/\/136.244.98.104\/media\/pizza\/pizza.png"
    },
    {
      "id": 2,
      "price": 6.5,
      "name": "Formagi",
      "img": "http:\/\/136.244.98.104\/media\/pizza\/pizza.png"
    },
    {
      "id": 3,
      "price": 4.99,
      "name": "Formaggi",
      "img": "http:\/\/136.244.98.104\/media\/pizza\/pizza.png"
    },
    {
      "id": 4,
      "price": 7,
      "name": "Prosciutto",
      "img": "http:\/\/136.244.98.104\/media\/pizza\/pizza.png"
    },
    {
      "id": 5,
      "price": 9.99,
      "name": "Prosciutto",
      "img": "http:\/\/136.244.98.104\/media\/pizza\/pizza.png"
    }
  ],
  "productPerPage": 5,
  "currentPage": 1,
  "productCount": 5,
  "filters": [
    {
      "name": "Epice",
      "code": "epice"
    },
    {
      "name": "Vegan",
      "code": "vegan"
    },
    {
      "name": "Surce",
      "code": "surce"
    }
  ]
}
""".trimIndent()
