package com.treewall.av.pizzaapp.domain.authorization

import com.treewall.av.pizzaapp.data.Result
import com.treewall.av.pizzaapp.data.authorization.TokenRepository
import com.treewall.av.pizzaapp.domain.BaseUseCase
import com.treewall.av.pizzaapp.domain.authorization.entity.TokenEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface GetTokenUseCase : BaseUseCase<Unit, Result<TokenEntity?>> {
    suspend operator fun invoke(): Result<TokenEntity?>
}

class GetTokenUseCaseImpl(
    private val repository: TokenRepository,
    private val ioDispatcher: CoroutineDispatcher
) : GetTokenUseCase {
    override suspend fun invoke() = invoke(Unit)

    override suspend fun invoke(parameters: Unit) = withContext(ioDispatcher) {
        repository.get()
    }
}