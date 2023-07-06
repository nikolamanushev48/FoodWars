package com.mentormate.foodwars.data

import com.mentormate.foodwars.R

sealed class Dialog

open class MessageDialog(
    val messageTextResource: Int? = null,
    val positiveButtonCallback: (() -> Unit)? = null,
    val negativeButtonCallback: (() -> Unit)? = null,
    val titleTextRes: Int? = null,
    val positiveButtonTextRes: Int? = null,
    val negativeButtonTextRes: Int? = null,
) : Dialog()

class NoNetworkDialog(
    posBtnClicked: (() -> Unit)? = null
) :
    MessageDialog(
        positiveButtonCallback = posBtnClicked,
        titleTextRes = R.string.no_network_dialog_title,
        messageTextResource = R.string.no_network_dialog_message,
        positiveButtonTextRes = R.string.setting,
    )