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

    override suspend fun save(request: TokenEntity) = Result.Success(saveEntity(request))

    private fun getEntity() = TokenEntity(preferences.getString(TOKEN_FIELD, null))

    private fun saveEntity(request: TokenEntity) = request.apply {
        preferences
            .edit()
            .putString(TOKEN_FIELD, request.token)
            .apply()
    }

    companion object {
        private val TOKEN_FIELD = "TOKEN_FIELD"
    }
}