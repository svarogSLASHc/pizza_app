package com.treewall.av.pizzaapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.treewall.av.pizzaapp.presentation.base.BaseViewModel

class MainActivityViewModel : BaseViewModel() {
    private val _locationSettings = MutableLiveData<Boolean>()
    val locationSettings: LiveData<Boolean> = _locationSettings

    fun setLocationSettingsEnabled(isEnabled: Boolean) {
        _locationSettings.value = isEnabled
    }
}