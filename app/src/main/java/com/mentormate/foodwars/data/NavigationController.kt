package com.mentormate.foodwars.data

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mentormate.foodwars.R
import com.mentormate.foodwars.ui.permission.PermissionResultProvider
import com.mentormate.foodwars.ui.result.ResultProvider
import com.mentormate.foodwars.utils.ProvideExtras

fun Fragment.navigateToDestination(destination: Destination) {
    when (destination) {
        is External -> handleExternalNavigation(destination)
        is Internal -> handleInternalNavigation(destination)
    }
}

fun Fragment.handleInternalNavigation(internalDestination: Internal) {
    when (internalDestination) {
        is ToDirection -> {
            findNavController().navigate(
                internalDestination.navigationId,
                internalDestination.arguments?.toBundle(),
                null,
                (this as? ProvideExtras)?.getExtra()
            )
        }
        is PopBack -> findNavController().popBackStack()
    }
}

fun Fragment.handleExternalNavigation(externalDestination: External) {
    when (externalDestination) {
        is ToGallery -> {
            if (this is ResultProvider) {
                provideObserver().launch()
            }
        }
        is CheckPermissions -> {
            if (this is PermissionResultProvider) {
                provideObserver().launch()
            }
        }
        is ToSettings -> {
            startActivity(Intent(Settings.ACTION_SETTINGS))
        }
        is ShareViaEmail -> {
            context?.createIntent(externalDestination)
        }
    }
}

@SuppressLint("StringFormatMatches")
private fun Context.createIntent(
    externalDestination: ShareViaEmail
) {
    Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(
            Intent.EXTRA_TEXT,
            getString(
                R.string.predefined_message,
                externalDestination.firstName,
                externalDestination.lastName,
                externalDestination.foodName,
                InterestText.values().find {
                    it.number == externalDestination.type
                }?.stringResource?.let { getString(it) }
            )
        )

        startActivity(
            Intent.createChooser(
                this@apply,
                getString(R.string.share_via)
            )
        )
    }
}

fun Map<String, Any?>.toBundle(): Bundle = bundleOf(*this.toList().toTypedArray())