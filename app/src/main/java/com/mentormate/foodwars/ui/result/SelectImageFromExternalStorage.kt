package com.mentormate.foodwars.ui.result

import android.content.Intent
import android.provider.MediaStore
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import java.util.*

const val IMAGE_RESULT_KEY = "imageResultKey"

interface ResultProvider {
    fun provideObserver(): ActivityResultHandler
}

interface ActivityResultHandler {
    fun launch()
}

class SelectImageFromExternalStorage(
    private val registry: ActivityResultRegistry,
    private val callback: (ActivityResult) -> Unit,
) : DefaultLifecycleObserver, ActivityResultHandler {

    lateinit var launcherIntent: ActivityResultLauncher<Intent>

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)

        launcherIntent = registry.register(
            IMAGE_RESULT_KEY,
            owner,
            ActivityResultContracts.StartActivityForResult()
        ) { result -> callback.invoke(result) }
    }

    override fun launch() {
        Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.INTERNAL_CONTENT_URI
        ).run {
            launcherIntent.launch(this)
        }
    }
}
