package com.mentormate.foodwars

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Looper
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationServices.getFusedLocationProviderClient
import java.util.*

private const val INTERVAL = 2000L

class LocationProvider(
    lifecycle: Lifecycle,
    context: Context,
    private val callback: (MutableList<Address>) -> Unit
) : DefaultLifecycleObserver {

    init {
        lifecycle.addObserver(this)
    }

    private val geocoder: Geocoder by lazy {
        Geocoder(context, Locale.getDefault())
    }

    private val fusedLocationProviderClient: FusedLocationProviderClient by lazy {
        getFusedLocationProviderClient(context)
    }

    private val locationCallback: LocationCallback by lazy {
        object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {

                if (locationResult.lastLocation != null) {
                    fusedLocationProviderClient.removeLocationUpdates(locationCallback)
                    geocoder.getFromLocation(
                        locationResult.locations.first().latitude,
                        locationResult.locations.first().longitude,
                        1
                    )?.let {
                        callback.invoke(
                            it
                        )
                    }
                }
            }
        }
    }

    private val locationRequest: LocationRequest by lazy {
        LocationRequest.Builder(Priority.PRIORITY_BALANCED_POWER_ACCURACY, INTERVAL)
            .setMinUpdateIntervalMillis(INTERVAL)
            .setMaxUpdateDelayMillis(INTERVAL)
            .build()
    }

    @SuppressLint("MissingPermission")
    fun updateUserLocation() {
        fusedLocationProviderClient.lastLocation.addOnCompleteListener {

            if (it.result == null) {
                fusedLocationProviderClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    Looper.getMainLooper()
                )
            } else {
                fusedLocationProviderClient.removeLocationUpdates(locationCallback)
                geocoder.getFromLocation(
                    it.result.latitude,
                    it.result.longitude,
                    1
                )?.let { it1 ->
                    callback.invoke(
                        it1
                    )
                }
            }
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }
}