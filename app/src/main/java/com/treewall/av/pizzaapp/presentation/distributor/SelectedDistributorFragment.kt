package com.treewall.av.pizzaapp.presentation.distributor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.treewall.av.pizzaapp.R
import com.treewall.av.pizzaapp.databinding.FragmentSelectedDistributorBinding
import com.treewall.av.pizzaapp.presentation.base.BaseFragment

class SelectedDistributorFragment :
    BaseFragment<SelectedDistributorViewModel>(SelectedDistributorViewModel::class) {
    private var googleMap: GoogleMap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentSelectedDistributorBinding.inflate(inflater, container, false).also {
            it.viewModel = viewModel
            it.lifecycleOwner = viewLifecycleOwner
        }.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeMap()
    }

    private fun initializeMap() {
        (childFragmentManager.findFragmentById(R.id.map_distributor) as? SupportMapFragment)?.getMapAsync {
            googleMap = it
            subscribeToPizzaMachine()
            it.uiSettings.isMapToolbarEnabled = false
        }
    }

    private fun subscribeToPizzaMachine() {
        viewModel.distributorLocation.observe(viewLifecycleOwner, Observer { machine ->
            val bitmapDescriptor =
                BitmapDescriptorFactory.fromResource(R.drawable.ic_pizza_pin_detail)
            googleMap?.addMarker(
                MarkerOptions().position(
                    LatLng(machine.latitude, machine.longitude)
                ).icon(bitmapDescriptor)
            )
            googleMap?.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(machine.latitude, machine.longitude),
                    ZOOM_LEVEL
                )
            )
        })
    }

    companion object {
        private const val ZOOM_LEVEL = 16f
    }
}