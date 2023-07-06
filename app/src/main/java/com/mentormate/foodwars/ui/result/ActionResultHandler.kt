package com.mentormate.foodwars.ui.result

import android.net.Uri

interface ActionResultHandler {
    fun onSuccess(
        uri: Uri?
    )

    fun onError(argument: Any? = null) {
        //do nothing
    }
}