package com.treewall.av.pizzaapp.data.authorization

import com.treewall.av.pizzaapp.data.authorization.dto.*
import com.treewall.av.pizzaapp.domain.authorization.entity.*
import com.treewall.av.pizzaapp.domain.authorization.logger.BaseLogger

interface AuthEntityTranslator {
    suspend fun createRegisterRequestDTO(entity: RegisterRequestEntity): RegisterRequestDTO
    suspend fun createCustomerEntity(dto: CustomerDTO): CustomerEntity
    suspend fun createLoginRequestDTO(request: LoginRequestEntity): LoginRequestDTO
    suspend fun createResetPasswordRequestDTO(request: ResetPasswordRequestEntity): ResetPasswordRequestDTO
    suspend fun createResetPasswordResponse(dto: ResetPasswordResponseDTO): ResetPasswordResponseEntity
}

class AuthEntityTranslatorImpl(
    private val logger: BaseLogger
) : AuthEntityTranslator {

    override suspend fun createRegisterRequestDTO(entity: RegisterRequestEntity): RegisterRequestDTO {
        logger.d("${Thread.currentThread().name} createRegisterRequestDTO()")
        return with(entity) {
            RegisterRequestDTO(
                createCustomerRequestDTO(createCustomerEntity),
                password
            )
        }
    }

    private fun createCustomerRequestDTO(createCustomerEntity: CreateCustomerEntity) =
        with(createCustomerEntity) {
            logger.d("${Thread.currentThread().name} createCustomRequestDTO()")
            CreateCustomerDTO(firstName, lastName, email)
        }

    override suspend fun createCustomerEntity(dto: CustomerDTO) =
        with(dto) {
            CustomerEntity(
                id,
                email,
                firstName,
                lastName,
                addresses.map { createAddressEntity(it) })
        }

    private fun createAddressEntity(dto: AddressDTO) = with(dto) {
        AddressEntity(
            defaultBilling,
            firstName,
            city,
            street,
            postcode,
            telephone,
            defaultShipping,
            createRegion(region),
            countryId,
            lastName
        )
    }

    private fun createRegion(dto: RegionDTO) =
        with(dto) { RegionEntity(regionCode, regionId, region) }

    override suspend fun createLoginRequestDTO(request: LoginRequestEntity): LoginRequestDTO {
        return LoginRequestDTO(request.userName, request.password)
    }

    override suspend fun createResetPasswordRequestDTO(request: ResetPasswordRequestEntity): ResetPasswordRequestDTO {
        logger.d("${Thread.currentThread().name} entered createResetPasswordRequestDTO($request)")
        return ResetPasswordRequestDTO(request.username)
    }

    override suspend fun createResetPasswordResponse(dto: ResetPasswordResponseDTO): ResetPasswordResponseEntity {
        logger.d("${Thread.currentThread().name} entered createResetPasswordResponse($dto)")
        return ResetPasswordResponseEntity(dto.message)
    }
}