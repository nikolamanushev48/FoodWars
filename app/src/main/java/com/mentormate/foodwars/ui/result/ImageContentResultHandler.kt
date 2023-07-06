package com.mentormate.foodwars.ui.result

import android.app.Activity
import android.net.Uri
import androidx.activity.result.ActivityResult

class ImageContentResultHandler(
    private val result: ActivityResult,
    private val callback: ActionResultHandler
) {
    fun handleResult(
        uri: Uri?
    ) {
        when (result.resultCode) {
            Activity.RESULT_OK -> {
                callback.onSuccess(uri)
            }
            Activity.RESULT_CANCELED -> {
                callback.onError()
            }
        }
    }
}