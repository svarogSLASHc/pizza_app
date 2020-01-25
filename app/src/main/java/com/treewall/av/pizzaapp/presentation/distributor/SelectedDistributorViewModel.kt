package com.treewall.av.pizzaapp.presentation.distributor

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.treewall.av.pizzaapp.R
import com.treewall.av.pizzaapp.data.Result
import com.treewall.av.pizzaapp.domain.authorization.GetSelectedDistributor
import com.treewall.av.pizzaapp.presentation.base.BackClickListener
import com.treewall.av.pizzaapp.presentation.base.BaseViewModel
import com.treewall.av.pizzaapp.utils.LocationData
import kotlinx.coroutines.launch

class SelectedDistributorViewModel(private val selectedDistributor: GetSelectedDistributor) :
    BaseViewModel(), BackClickListener {
    val name = ObservableField<String>()
    val address = ObservableField<String>()
    val distance = ObservableField<String>()
    private val _distributorLocation = MutableLiveData<LocationData>()
    val distributorLocation: LiveData<LocationData>
        get() = _distributorLocation

    init {
        viewModelScope.launch {
            with(selectedDistributor.invoke()) {
                when (this) {
                    is Result.Success -> {
                        name.set(data.distributor.name)
                        address.set(data.distributor.address)
                        _distributorLocation.postValue(
                            LocationData(
                                data.distributor.latitude.toDouble(),
                                data.distributor.longitude.toDouble()
                            )
                        )
                    }
                    else -> navigateBack()
                }
            }
        }
    }

    fun onTakeAwayClick() {
        navigate(R.id.action_selectedDistributorFragment_to_productListFragment)
    }

    override fun onCLick() {
        navigateBack()
    }
}