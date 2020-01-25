package com.treewall.av.pizzaapp.data.authorization

import com.google.gson.Gson
import com.treewall.av.pizzaapp.data.Result
import com.treewall.av.pizzaapp.data.base.BaseRemoteDataSource
import com.treewall.av.pizzaapp.domain.authorization.entity.*

interface RemoteAuthDataSource {

    suspend fun login(requestEntity: LoginRequestEntity): Result<String>

    suspend fun registerCustomer(requestEntity: RegisterRequestEntity): Result<CustomerEntity>

    suspend fun resetPassword(requestEntity: ResetPasswordRequestEntity): Result<ResetPasswordResponseEntity>
}

class RemoteAuthDataSourceImpl(
    private val service: AuthorizationService,
    private val entityTranslator: AuthEntityTranslator,
    gsonConverter: Gson
) : BaseRemoteDataSource(gsonConverter), RemoteAuthDataSource {

    override suspend fun login(requestEntity: LoginRequestEntity): Result<String> {
        val request = entityTranslator.createLoginRequestDTO(requestEntity)
        return processResponse({ service.login(request) }, { it })
    }

    override suspend fun registerCustomer(requestEntity: RegisterRequestEntity): Result<CustomerEntity> {
        val request = entityTranslator.createRegisterRequestDTO(requestEntity)
        return processResponse(
            { service.registerCustomer(request) },
            entityTranslator::createCustomerEntity
        )
    }

    override suspend fun resetPassword(requestEntity: ResetPasswordRequestEntity): Result<ResetPasswordResponseEntity> {
        val request = entityTranslator.createResetPasswordRequestDTO(requestEntity)
        return processResponse(
            { service.resetPassword(request) },
            entityTranslator::createResetPasswordResponse
        )
    }
}