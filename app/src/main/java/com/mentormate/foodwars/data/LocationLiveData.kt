package com.mentormate.foodwars.data

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.os.Looper
import androidx.lifecycle.LiveData
import com.google.android.gms.location.*
import com.mentormate.foodwars.ui.constants.INTERVAL_MS
import com.mentormate.foodwars.ui.constants.MAX_UPDATE_DELAY_MS
import com.mentormate.foodwars.ui.constants.MIN_UPDATE_INTERVAL_MS
import java.util.*

class LocationLiveData(context: Context) : LiveData<String?>() {

    private val geocoder: Geocoder by lazy {
        Geocoder(context, Locale.getDefault())
    }

    private val fusedLocationProviderClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    private val locationRequest: LocationRequest by lazy {
        LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            INTERVAL_MS
        )
            .setWaitForAccurateLocation(false)
            .setMinUpdateIntervalMillis(MIN_UPDATE_INTERVAL_MS)
            .setMaxUpdateDelayMillis(MAX_UPDATE_DELAY_MS)
            .build()
    }

    private val locationCallback: LocationCallback by lazy {
        object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                locationResult.locations.first()?.let { firstLocation ->
                    value = convertCoordinatesToAddress(firstLocation)
                }
            }
        }
    }

    private fun convertCoordinatesToAddress(location: Location): String? {
        geocoder.getFromLocation(location.latitude, location.longitude, 1)
            ?.firstOrNull()?.let { address ->
                address.let {
                    stopLocationUpdates()
                    return "${address.countryName} ${address.adminArea} ${address.subAdminArea}"
                }
            }
        startLocationUpdates()
        return null
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    @SuppressLint("MissingPermission")
    private fun getUserLocation() {
        fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
            task.result?.let { location ->
                value = convertCoordinatesToAddress(location)
                //TODO: Add the address to the user
            } ?: run {
                startLocationUpdates()
            }
        }
    }

    override fun onActive() {
        super.onActive()
        getUserLocation()
    }

    override fun onInactive() {
        super.onInactive()
        stopLocationUpdates()
    }
}