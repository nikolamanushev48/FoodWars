package com.mentormate.foodwars.data

import android.content.DialogInterface
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mentormate.foodwars.R
import com.mentormate.foodwars.ui.constants.INVALID_RES

fun Fragment.handleDialog(dialog: Dialog): Dialog =
    when (dialog) {
        is MessageDialog -> dialog.apply {
            buildMessageDialog(this)
        }
    }

fun Fragment.buildMessageDialog(dialog: MessageDialog) =
    with(MaterialAlertDialogBuilder(requireContext())) {
        dialog.titleTextRes?.let { titleTextRes ->
            setTitle(getString(titleTextRes))
        }
        dialog.messageTextResource?.let { messageTextResource ->
            setMessage(getString(messageTextResource))
        }
        dialog.positiveButtonTextRes?.let { positiveButtonTextRes ->
            setPositiveButton(getString(positiveButtonTextRes)) { _: DialogInterface?, _: Int ->
                dialog.positiveButtonCallback?.invoke()
            }
        }
        dialog.negativeButtonTextRes?.let { negativeButtonTextRes ->
            setNegativeButton(getString(negativeButtonTextRes)) { _: DialogInterface?, _: Int ->
                dialog.negativeButtonCallback?.invoke()
            }
        }
            ?.create()?.show()
    }

