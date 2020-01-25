package com.treewall.av.pizzaapp.domain.authorization

import com.treewall.av.pizzaapp.data.Result
import com.treewall.av.pizzaapp.data.authorization.AuthorizationRepository
import com.treewall.av.pizzaapp.domain.BaseUseCase
import com.treewall.av.pizzaapp.domain.authorization.entity.ResetPasswordRequestEntity
import com.treewall.av.pizzaapp.domain.authorization.entity.ResetPasswordResponseEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface ResetPasswordUseCase : BaseUseCase<String, Result<ResetPasswordResponseEntity>>

class ResetPasswordUseCaseImpl(
    private val repository: AuthorizationRepository,
    private val ioDispatcher: CoroutineDispatcher
) : ResetPasswordUseCase {

    override suspend fun invoke(parameters: String) = withContext(ioDispatcher) {
        repository.resetPassword(ResetPasswordRequestEntity(parameters))
    }
}