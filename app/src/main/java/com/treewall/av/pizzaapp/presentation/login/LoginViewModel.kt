package com.treewall.av.pizzaapp.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.treewall.av.pizzaapp.R
import com.treewall.av.pizzaapp.data.Result
import com.treewall.av.pizzaapp.domain.authorization.LoginUseCase
import com.treewall.av.pizzaapp.domain.authorization.entity.LoginRequestEntity
import com.treewall.av.pizzaapp.domain.authorization.logger.BaseLogger
import com.treewall.av.pizzaapp.presentation.base.BackClickListener
import com.treewall.av.pizzaapp.presentation.base.BaseViewModel
import com.treewall.av.pizzaapp.presentation.base.SingleLiveEvent
import com.treewall.av.pizzaapp.utils.UserCredentialsValidator
import kotlinx.coroutines.launch

class LoginViewModel constructor(
    private val loginUseCase: LoginUseCase,
    private val userCredentialsValidator: UserCredentialsValidator,
    private val logger: BaseLogger
) : BaseViewModel(), BackClickListener {

    private val _errorMessage = SingleLiveEvent<String>()
    val errorMessage = _errorMessage
    private val _emailError = MutableLiveData<Int?>(null)
    val emailError: LiveData<Int?> = _emailError
    private val _passwordError = MutableLiveData<Int?>(null)
    val passwordError: LiveData<Int?> = _passwordError


    override fun onCLick() {
        navigateBack()
    }

    fun login(email: String, password: String) {
        if (loginValid(email) and passwordValid(password)) {
            processLogin(email, password)
        }
    }

    private fun loginValid(email: String): Boolean {
        _emailError.value = userCredentialsValidator.getEmailErrorIfInvalid(email)
        return _emailError.value == null
    }

    private fun passwordValid(password: String): Boolean {
        _passwordError.value = userCredentialsValidator.getPasswordErrorIfInvalid(password)
        return _passwordError.value == null
    }

    fun forgotClick() {
        navigate(R.id.action_loginFragment_to_forgotPasswordViewFragment)
    }

    private fun processLogin(email: String, password: String) {
        _loading.value = true
        viewModelScope.launch {
            loginUseCase(LoginRequestEntity(email, password)).apply {
                _loading.value = false
                when (this) {
                    is Result.Success -> {
                        logger.d("Saved token :${this.data}")
                        navigate(R.id.action_loginFragment_to_mapViewFragment)
                    }
                    is Result.Error -> {
                        _errorMessage.postValue(this.response?.message)
                    }
                }
            }
        }
    }

}