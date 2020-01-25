package com.treewall.av.pizzaapp.presentation.splash

import androidx.lifecycle.viewModelScope
import com.treewall.av.pizzaapp.R
import com.treewall.av.pizzaapp.data.Result
import com.treewall.av.pizzaapp.domain.authorization.GetTokenUseCase
import com.treewall.av.pizzaapp.domain.authorization.logger.BaseLogger
import com.treewall.av.pizzaapp.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class SplashViewModel constructor(
    private val getTokenUseCase: GetTokenUseCase,
    private val logger: BaseLogger
) : BaseViewModel() {

    fun onCreate() {
        viewModelScope.launch {
            getTokenUseCase().apply {
                when (this) {
                    is Result.Success -> if (data?.token !== null) {
                        navigate(R.id.action_splashFragment_to_mapViewFragment)
                    } else {
                        navigate(R.id.action_splashFragment_to_welcomeFragment)
                    }
                }
            }
        }
    }
}