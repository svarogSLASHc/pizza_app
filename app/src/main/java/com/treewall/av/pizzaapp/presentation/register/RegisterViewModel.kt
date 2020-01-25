package com.treewall.av.pizzaapp.presentation.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.treewall.av.pizzaapp.R
import com.treewall.av.pizzaapp.data.Result.Error
import com.treewall.av.pizzaapp.data.Result.Success
import com.treewall.av.pizzaapp.domain.authorization.RegisterUseCase
import com.treewall.av.pizzaapp.domain.authorization.entity.RegisterRequestEntity
import com.treewall.av.pizzaapp.presentation.base.BackClickListener
import com.treewall.av.pizzaapp.presentation.base.BaseViewModel
import com.treewall.av.pizzaapp.presentation.base.SingleLiveEvent
import com.treewall.av.pizzaapp.utils.UserCredentialsValidator
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase,
    private val userCredentialsValidator: UserCredentialsValidator
) : BaseViewModel(), BackClickListener {

    private val _emailError = MutableLiveData<Int?>(null)
    val emailError: LiveData<Int?> = _emailError
    private val _nameError = MutableLiveData<Int?>(null)
    val nameError: LiveData<Int?> = _nameError
    private val _passwordError = MutableLiveData<Int?>(null)
    val passwordError: LiveData<Int?> = _passwordError
    private val _confirmPasswordError = MutableLiveData<Int?>(null)
    val confirmPasswordError: LiveData<Int?> = _confirmPasswordError
    private val _errorMessage = SingleLiveEvent<String>()
    val errorMessage: LiveData<String> = _errorMessage


    override fun onCLick() {
        navigateBack()
    }

    fun register(name: String, email: String, password: String, confirmPassword: String) {
        if (nameValid(name) && emailValid(email) && passwordValid(password) && confirmPasswordValid(
                password,
                confirmPassword
            )
        ) {
            viewModelScope.launch {
                _loading.value = true
                registerUseCase(RegisterRequestEntity(name, " ", email, password)).apply {
                    _loading.value = false
                    when (this) {
                        is Success -> {
                            navigate(R.id.action_registerFragment_to_loginFragment)
                        }
                        is Error -> {
                            this.response?.message?.let { _errorMessage.value = it }
                        }
                    }
                }
            }
        }
    }

    private fun nameValid(name: String): Boolean {
        _nameError.value = userCredentialsValidator.getNameErrorIfInvalid(name)
        return _nameError.value == null
    }

    private fun emailValid(email: String): Boolean {
        _emailError.value = userCredentialsValidator.getEmailErrorIfInvalid(email)
        return _emailError.value == null
    }

    private fun passwordValid(password: String): Boolean {
        _passwordError.value = userCredentialsValidator.getPasswordErrorIfInvalid(password)
        return _passwordError.value == null
    }

    private fun confirmPasswordValid(password: String, confirmPassword: String): Boolean {
        _confirmPasswordError.value = userCredentialsValidator.getConfirmPasswordErrorIfInvalid(
            password, confirmPassword
        )
        return _confirmPasswordError.value == null
    }

}