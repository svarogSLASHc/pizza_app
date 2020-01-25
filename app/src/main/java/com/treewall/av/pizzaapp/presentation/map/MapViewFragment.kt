package com.treewall.av.pizzaapp.presentation.map

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.treewall.av.pizzaapp.MainActivityViewModel
import com.treewall.av.pizzaapp.R
import com.treewall.av.pizzaapp.databinding.FragmentMapViewBinding
import com.treewall.av.pizzaapp.presentation.base.BaseFragment
import com.treewall.av.pizzaapp.utils.LocationData
import kotlinx.android.synthetic.main.fragment_map_view.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import permissions.dispatcher.*
import java.util.*

@RuntimePermissions
class MapViewFragment : BaseFragment<MapViewModel>(MapViewModel::class) {

    companion object {
        const val REQUEST_CHECK_SETTINGS = 123
        const val REQUEST_PLACES = 143
    }

    private var googleMap: GoogleMap? = null
    private val activityViewModel: MainActivityViewModel by sharedViewModel()
    private val markers: MutableList<Marker> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Places.initialize(requireActivity(), getString(R.string.google_places_api))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentMapViewBinding.inflate(inflater, container, false).also {
            it.viewModel = viewModel
            it.lifecycleOwner = viewLifecycleOwner
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeMap()
        checkLocationSettingsWithPermissionCheck()
        subscribeToError()
        et_search.setOnClickListener { startAutocomplete() }
    }

    private fun initializeMap() {
        (childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment)?.getMapAsync {
            googleMap = it
            subscribeToUserLocation()
            subscribeToPizzaMachines()
            googleMap?.setOnMarkerClickListener { marker ->
                viewModel.distributorSelected(
                    marker?.position?.latitude!!,
                    marker.position?.longitude!!
                )
                true
            }
            if (PackageManager.PERMISSION_GRANTED
                == ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                it.isMyLocationEnabled = true
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    private fun subscribeToError() {
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        })
    }

    private fun startAutocomplete() {
        val fields = Arrays.asList(Place.Field.LAT_LNG)
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
            .setCountry("FR")
            .build(requireActivity())
        startActivityForResult(intent, REQUEST_PLACES)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        logger.d(
            "onActivityResult(), (requestCode == REQUEST_PLACES) is ${requestCode == REQUEST_PLACES}, " +
                    "(resultCode == Activity.RESULT_OK) is ${resultCode == Activity.RESULT_OK}, " +
                    "data = ${data?.let { Autocomplete.getStatusFromIntent(it) }}"
        )
        if (requestCode == REQUEST_PLACES && resultCode == Activity.RESULT_OK) {
            data?.let { Autocomplete.getPlaceFromIntent(it).latLng }?.let {
                viewModel.placeSelected(it.latitude, it.longitude)
            }
        } else if (requestCode == REQUEST_PLACES) {
            //TODO FAKE PLACE JUST FOR TESTING VIEWMODEL LOGIC
            logger.d("FAKE place selected ")
            viewModel.placeSelected(45.748464, 4.84671)
        }
    }

    private fun subscribeToUserLocation() {
        logger.d("Entered subscribeToUserLocation()")
        viewModel.userLocation.observe(viewLifecycleOwner, Observer { location ->
            logger.d("Location found $location")
            location?.let { moveMapCamera(it) }
        })
    }

    private fun moveMapCamera(locationData: LocationData) {
        googleMap?.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(locationData.latitude, locationData.longitude),
                12f
            ), null
        )
    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun checkLocationSettings() {
        logger.d("Entered checkLocationSettings()")
        subscribeToLocationSettings()
        val locationRequest =
            LocationRequest().apply { priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY }
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val client: SettingsClient = LocationServices.getSettingsClient(requireActivity())
        client.checkLocationSettings(builder.build()).addOnFailureListener {
            logger.d("Location settings failed")
            if (it is ResolvableApiException) {
                try {
                    logger.d("Location settings trying to ask user turn on")
                    it.startResolutionForResult(requireActivity(), REQUEST_CHECK_SETTINGS)
                } catch (exc: IntentSender.SendIntentException) {
                    exc.printStackTrace()
                    viewModel.locationDisabled()
                }
            } else {
                viewModel.locationDisabled()
            }
        }.addOnSuccessListener {
            logger.d("Location and settings are enabled")
            locationEnabled()
        }.addOnCanceledListener {
            logger.d("Location settings canceled")
            viewModel.locationDisabled()
        }
    }

    private fun subscribeToLocationSettings() {
        logger.d("Entered subscribeToLocationSettings()")
        activityViewModel.locationSettings.observe(viewLifecycleOwner, Observer {
            logger.d("Location settings isEnabled = $it")
            if (it) locationEnabled() else viewModel.locationDisabled()
        })
    }

    private fun locationEnabled() {
        logger.d("Entered locationEnabled()")
        googleMap?.isMyLocationEnabled = true
        viewModel.locationEnabled()
    }

    @OnShowRationale(Manifest.permission.ACCESS_FINE_LOCATION)
    fun showRationaleForLocation(request: PermissionRequest) {
        logger.d("Entered showRationaleForLocation()")
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.map_view_location_permission_title)
            .setMessage(R.string.map_view_location_permission_description)
            .setPositiveButton(android.R.string.ok) { _, _ -> request.proceed() }
            .setNegativeButton(android.R.string.cancel) { _, _ -> request.cancel() }
            .show()
    }

    @OnPermissionDenied(Manifest.permission.ACCESS_FINE_LOCATION)
    fun onLocationDenied() {
        logger.d("Entered onLocationDenied()")
        viewModel.locationDisabled()
    }

    private fun subscribeToPizzaMachines() {
        viewModel.pizzaMachines.observe(viewLifecycleOwner, Observer { machines ->
            val bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_pizza_pin)
            markers.forEach { it.remove() }
            machines.forEach {
                val marker: Marker? = googleMap?.addMarker(
                    MarkerOptions().position(
                        LatLng(it.latitude.toDouble(), it.longitude.toDouble())
                    ).icon(bitmapDescriptor)
                )
                if (marker != null) markers.add(marker)
            }
        })
    }
}