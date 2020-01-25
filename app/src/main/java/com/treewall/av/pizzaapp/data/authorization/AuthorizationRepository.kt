package com.treewall.av.pizzaapp.data.authorization

import com.treewall.av.pizzaapp.data.Result
import com.treewall.av.pizzaapp.domain.authorization.entity.*

interface AuthorizationRepository {

    suspend fun login(request: LoginRequestEntity): Result<String>

    suspend fun registerCustomer(request: RegisterRequestEntity): Result<CustomerEntity>

    suspend fun resetPassword(request: ResetPasswordRequestEntity): Result<ResetPasswordResponseEntity>
}

class AuthorizationRepositoryImpl(
    private val remoteDataSource: RemoteAuthDataSource
) : AuthorizationRepository {

    override suspend fun login(request: LoginRequestEntity) =
        remoteDataSource.login(request)

    override suspend fun registerCustomer(request: RegisterRequestEntity) =
        remoteDataSource.registerCustomer(request)

    override suspend fun resetPassword(request: ResetPasswordRequestEntity) =
        remoteDataSource.resetPassword(request)
}