package com.treewall.av.pizzaapp.domain.authorization

import com.treewall.av.pizzaapp.data.Result
import com.treewall.av.pizzaapp.data.authorization.AuthorizationRepository
import com.treewall.av.pizzaapp.data.authorization.TokenRepository
import com.treewall.av.pizzaapp.domain.BaseUseCase
import com.treewall.av.pizzaapp.domain.authorization.entity.LoginRequestEntity
import com.treewall.av.pizzaapp.domain.authorization.entity.TokenEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface LoginUseCase : BaseUseCase<LoginRequestEntity, Result<String>>

class LoginUseCaseImpl(
    private val repository: AuthorizationRepository,
    private val tokenRepository: TokenRepository,
    private val ioDispatcher: CoroutineDispatcher
) : LoginUseCase {

    override suspend operator fun invoke(parameters: LoginRequestEntity) =
        withContext(ioDispatcher) {
            repository.login(parameters).also {
                if (it is Result.Success)
                    tokenRepository.save(TokenEntity(it.data))
            }
        }
}