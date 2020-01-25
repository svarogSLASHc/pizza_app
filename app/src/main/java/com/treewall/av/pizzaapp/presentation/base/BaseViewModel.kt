package com.treewall.av.pizzaapp.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    protected val _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean> = _loading
    private val _navigationEvent = SingleLiveEvent<Navigation>()
    val navigationEvent: LiveData<Navigation> = _navigationEvent

    protected fun navigateBack() {
        _navigationEvent.postValue(Navigation.Back)
    }

    protected fun navigate(destination: Int) {
        _navigationEvent.postValue(Navigation.NavigationEvent(destination))
    }
}

sealed class Navigation {
    data class NavigationEvent(val destination: Int) : Navigation()

    object Back : Navigation()
}
