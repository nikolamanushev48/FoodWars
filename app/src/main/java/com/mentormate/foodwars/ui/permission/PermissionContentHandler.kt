package com.mentormate.foodwars.ui.permission

import android.Manifest
import android.app.Activity
import android.content.Context
import android.location.LocationManager
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import com.mentormate.foodwars.data.LocationLiveData

class PermissionContentHandler(
    private val context: Context,
    private val result: Boolean,
    private val callback: PermissionHandler
) {
    private val locationLiveData: LocationLiveData by lazy {
        LocationLiveData(context)
    }

    fun handleResult() {
        when {
            result -> {
                callback.onSuccess(locationLiveData, locationCheck(context))
            }
            shouldShowRequestPermissionRationale(
                context as Activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                callback.onError(true)
            }
            else -> {
                callback.onError(false)
            }
        }
    }

    private fun locationCheck(context: Context) =
        (context.getSystemService(Context.LOCATION_SERVICE) as LocationManager).run {
            isProviderEnabled(LocationManager.GPS_PROVIDER) && isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        }
}