package com.mentormate.foodwars.ui.permission

import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

interface PermissionResultProvider {
    fun provideObserver(): PermissionLiveDataHandler
}

interface ActivityResultHandler {
    fun launch()
}

class PermissionLiveDataHandler(
    private val registry: ActivityResultRegistry,
    private val permission: String,
    private val callback: (Boolean) -> Unit,
) : DefaultLifecycleObserver, ActivityResultHandler {

    lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)

        requestPermissionLauncher = registry.register(
            "",
            owner,
            ActivityResultContracts.RequestPermission()
        ) { result -> callback.invoke(result) }
    }

    override fun launch() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(
                permission
            )
        }
    }
}