package com.treewall.av.pizzaapp.domain.authorization

import com.treewall.av.pizzaapp.data.Result
import com.treewall.av.pizzaapp.data.authorization.AuthorizationRepository
import com.treewall.av.pizzaapp.domain.BaseUseCase
import com.treewall.av.pizzaapp.domain.authorization.entity.CustomerEntity
import com.treewall.av.pizzaapp.domain.authorization.entity.RegisterRequestEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface RegisterUseCase : BaseUseCase<RegisterRequestEntity, Result<CustomerEntity>>

class RegisterUseCaseImpl(
    private val repository: AuthorizationRepository,
    private val ioDispatcher: CoroutineDispatcher
) : RegisterUseCase {

    override suspend operator fun invoke(parameters: RegisterRequestEntity) =
        withContext(ioDispatcher) {
            repository.registerCustomer(parameters)
        }
}