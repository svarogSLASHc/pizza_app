package com.treewall.av.pizzaapp.presentation.map

import androidx.lifecycle.*
import com.treewall.av.pizzaapp.R
import com.treewall.av.pizzaapp.data.Result
import com.treewall.av.pizzaapp.data.Result.Error
import com.treewall.av.pizzaapp.data.Result.Success
import com.treewall.av.pizzaapp.domain.SelectedDistributor
import com.treewall.av.pizzaapp.domain.authorization.SaveSelectedDistributor
import com.treewall.av.pizzaapp.domain.authorization.logger.BaseLogger
import com.treewall.av.pizzaapp.domain.pizza.GetPizzaMachinesByGeoUseCase
import com.treewall.av.pizzaapp.domain.pizza.entity.PinLocation
import com.treewall.av.pizzaapp.domain.pizza.entity.PizzaMachineEntity
import com.treewall.av.pizzaapp.domain.pizza.entity.PizzaRequest
import com.treewall.av.pizzaapp.presentation.base.BaseViewModel
import com.treewall.av.pizzaapp.presentation.base.SingleLiveEvent
import com.treewall.av.pizzaapp.utils.GetLocationManager
import com.treewall.av.pizzaapp.utils.LocationData
import kotlinx.coroutines.launch

class MapViewModel(
    private val getLocationManager: GetLocationManager,
    private val getPizzaMachinesUseCase: GetPizzaMachinesByGeoUseCase,
    private val saveDistributor: SaveSelectedDistributor,
    private val logger: BaseLogger
) : BaseViewModel() {

    private val lionLocation = LocationData(45.748464, 4.84671)
    private val userLocationAvailability = MutableLiveData<Boolean>()
    private val lastSelectedPlace = MutableLiveData<LocationData?>()
    private val _errorMessage = SingleLiveEvent<String>()

    val errorMessage: LiveData<String> = _errorMessage
    val userLocation: LiveData<LocationData?> = initializeUserLocationData()
    val pizzaMachines: LiveData<List<PizzaMachineEntity>> =
        Transformations.switchMap(userLocation, this::getPizzaMachines)

    private fun initializeUserLocationData() = MediatorLiveData<LocationData?>().apply {
        var lastLocation: LocationData? = null
        var lastPlace: LocationData? = null

        addSource(getUserLocationLiveData()) {
            if (lastLocation != it && lastPlace == null) {
                logger.d("MapViewModel location changed latitude = ${it?.latitude}, longitude = ${it?.longitude}")
                lastLocation = it
                value = it
            }
        }
        addSource(lastSelectedPlace) {
            if (lastPlace != it) {
                logger.d("MapViewModel last place changed latitude = ${it?.latitude}, longitude = ${it?.longitude}")
                lastPlace = it
                value = it
            }
        }
    }

    private fun getUserLocationLiveData(): LiveData<LocationData?> {
        return Transformations.switchMap(userLocationAvailability) { available ->
            logger.d("Location availability changed = $available")
            if (available) {
                loadUserLocation()
            } else {
                MutableLiveData(lionLocation)
            }
        }
    }

    private fun loadUserLocation(): LiveData<LocationData> {
        return Transformations.map(getLocationManager.getLocation()) { result ->
            logger.d("Result from location manager = $result")
            when (result) {
                is Success -> result.data
                is Result.Loading -> null
                else -> lionLocation
            }
        }
    }

    fun locationEnabled() {
        userLocationAvailability.value = true
    }

    fun locationDisabled() {
        userLocationAvailability.value = false
    }

    fun distributorSelected(latitude: Double, longitude: Double) {
        pizzaMachines.value
            ?.firstOrNull { it ->
                it.latitude.toDouble() == latitude && it.longitude.toDouble() == longitude
            }?.let {
                viewModelScope.launch {
                    logger.d("Selected pizzaMachines $it")
                    saveDistributor(SelectedDistributor(it, userLocation.value?.run {
                        PinLocation(latitude, longitude)
                    }))
                }
                navigate(R.id.action_mapViewFragment_to_selectedDistributorFragment)
            }
        logger.d("Selected $latitude : $longitude")
    }

    private fun getPizzaMachines(locationData: LocationData?) =
        liveData(viewModelScope.coroutineContext) {
            if (locationData == null) {
                logger.d("Location data is null, returning empty list pizza machines")
                emit(emptyList())
            } else {
                val result = getPizzaMachinesUseCase(
                    PizzaRequest(locationData.latitude, locationData.longitude)
                )
                logger.d("Loaded pizza machines result = $result")
                when (result) {
                    is Success -> emit(result.data.machines)
                    is Error -> {
                        result.response?.let { _errorMessage.value = it.message }
                        result.data?.let { emit(it.machines) } ?: emit(emptyList())
                    }
                    else -> emit(emptyList())
                }
            }
        }

    fun placeSelected(latitude: Double, longitude: Double) {
        logger.d("MapViewModel placeSelected(latitude = $latitude, longitude = $longitude")
        lastSelectedPlace.value = LocationData(latitude, longitude)
    }
}