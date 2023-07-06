package com.mentormate.foodwars.ui.permission

import com.mentormate.foodwars.data.LocationLiveData

interface PermissionHandler {

    fun onSuccess(
        locationLiveData: LocationLiveData,
        checkAvailableAddress: Boolean
    )

    fun onError(argument: Any? = null) {
        //do nothing
    }
}