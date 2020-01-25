package com.treewall.av.pizzaapp.presentation.welcome

import com.treewall.av.pizzaapp.R
import com.treewall.av.pizzaapp.presentation.base.BaseViewModel

class WelcomeViewModel : BaseViewModel() {

    fun loginClick() {
        navigate(R.id.action_welcomeFragment_to_loginFragment)
    }

    fun registerClick() {
        navigate(R.id.action_welcomeFragment_to_registerFragment)
    }
}