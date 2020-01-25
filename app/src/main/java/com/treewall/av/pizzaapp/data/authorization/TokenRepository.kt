package com.treewall.av.pizzaapp.data.authorization

import com.treewall.av.pizzaapp.data.Result
import com.treewall.av.pizzaapp.domain.authorization.entity.TokenEntity

interface TokenRepository {

    suspend fun get(): Result<TokenEntity?>

    suspend fun save(request: TokenEntity): Result<TokenEntity>
}

class TokenRepositoryImpl(
    private val dataStore: TokenDataStore
) : TokenRepository {
    override suspend fun get() = dataStore.get()

    override suspend fun save(request: TokenEntity) = dataStore.save(request)
}