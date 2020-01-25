package com.treewall.av.pizzaapp.data.authorization

import android.content.SharedPreferences
import com.treewall.av.pizzaapp.data.Result
import com.treewall.av.pizzaapp.domain.authorization.entity.TokenEntity

interface TokenDataStore {
    suspend fun get(): Result<TokenEntity?>

    suspend fun save(request: TokenEntity): Result<TokenEntity>
}

class TokenDataStoreImpl(
    private val preferences: SharedPreferences
) : TokenDataStore {
    override suspend fun get() = Result.Success(getEntity())

    override suspend fun save(request: TokenEntity) = Result.Success(request)

    private fun getEntity() = TokenEntity("z8vcvk8p28ack08zvxbpf0ap6gcz06c8")
}