package com.treewall.av.pizzaapp.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.treewall.av.pizzaapp.data.Result
import com.treewall.av.pizzaapp.domain.authorization.logger.BaseLogger

data class LocationData(
    val latitude: Double,
    val longitude: Double
)

interface GetLocationManager {
    fun getLocation(): LiveData<Result<LocationData>>
}

class GetLocationManagerImpl(
    private val context: Context,
    private val logger: BaseLogger
) : GetLocationManager {

    override fun getLocation(): LiveData<Result<LocationData>> {
        logger.d("Entered getLocation()")
        val liveData = MutableLiveData<Result<LocationData>>(Result.Loading(null))

        val isGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        if (!isGranted) {
            logger.d("Permissions not granted")
            liveData.postValue(Result.Error(null, null, null))
            return liveData
        }

        logger.d("Permissions granted")
        LocationServices.getFusedLocationProviderClient(context)
            .requestLocationUpdates(
                LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(5000), object : LocationCallback() {
                    override fun onLocationResult(result: LocationResult?) {
                        logger.d("Location found")
                        LocationServices.getFusedLocationProviderClient(context)
                            .removeLocationUpdates(this)
                        if (result == null) {
                            logger.d("Location null")
                            liveData.postValue(Result.Error(null, null, null))
                            return
                        }

                        logger.d("Location not null, returning ${result.lastLocation.latitude}, ${result.lastLocation.longitude}")
                        liveData.postValue(
                            Result.Success(
                                LocationData(
                                    result.lastLocation.latitude, result.lastLocation.longitude
                                )
                            )
                        )
                    }
                }, null
            )

        return liveData
    }
}