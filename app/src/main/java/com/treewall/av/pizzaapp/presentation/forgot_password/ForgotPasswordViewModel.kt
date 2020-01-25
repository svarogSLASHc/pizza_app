package com.treewall.av.pizzaapp.presentation.forgot_password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.treewall.av.pizzaapp.data.Result.Error
import com.treewall.av.pizzaapp.data.Result.Success
import com.treewall.av.pizzaapp.domain.authorization.ResetPasswordUseCase
import com.treewall.av.pizzaapp.presentation.base.BackClickListener
import com.treewall.av.pizzaapp.presentation.base.BaseViewModel
import com.treewall.av.pizzaapp.utils.UserCredentialsValidator
import kotlinx.coroutines.launch

class ForgotPasswordViewModel constructor(
    private val userCredentialsValidator: UserCredentialsValidator,
    private val resetPasswordUseCase: ResetPasswordUseCase
) : BaseViewModel(), BackClickListener {

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean>
        get() = _showDialog
    private val _emailError = MutableLiveData<Int?>(null)
    val emailError: LiveData<Int?> = _emailError
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun reset(email: String) {
        if (validEmail(email)) {
            viewModelScope.launch {
                _loading.value = true
                val result = resetPasswordUseCase(email)
                _loading.value = false
                when (result) {
                    is Success -> _showDialog.postValue(true)
                    is Error -> {
                        result.response?.message?.let { _errorMessage.value = it }
                    }
                }
            }
        }
    }

    private fun validEmail(email: String): Boolean {
        _emailError.value = userCredentialsValidator.getEmailErrorIfInvalid(email)
        return _emailError.value == null
    }

    override fun onCLick() {
        navigateBack()
    }
}