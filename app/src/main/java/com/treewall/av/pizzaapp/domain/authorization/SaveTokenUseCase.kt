package com.treewall.av.pizzaapp.domain.authorization

import com.treewall.av.pizzaapp.data.Result
import com.treewall.av.pizzaapp.data.authorization.TokenRepository
import com.treewall.av.pizzaapp.domain.BaseUseCase
import com.treewall.av.pizzaapp.domain.authorization.entity.TokenEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface SaveTokenUseCase : BaseUseCase<TokenEntity, Result<TokenEntity>>

class SaveTokenUseCaseImpl(
    private val repository: TokenRepository,
    private val ioDispatcher: CoroutineDispatcher
) : SaveTokenUseCase {
    override suspend fun invoke(parameters: TokenEntity) = withContext(ioDispatcher) {
        repository.save(parameters)
    }
}