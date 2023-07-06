package com.mentormate.foodwars.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mentormate.foodwars.R
import com.mentormate.foodwars.SingleLiveEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import timber.log.Timber

open class BaseViewModel : ViewModel() {
    val navigation: LiveData<Destination>
        get() = _navigation
    protected val _navigation: SingleLiveEvent<Destination> by lazy {
        SingleLiveEvent()
    }

    val dialog: LiveData<Dialog>
        get() = _dialog
    protected val _dialog: SingleLiveEvent<Dialog> by lazy {
        SingleLiveEvent()
    }

    protected val handler = CoroutineExceptionHandler { _, exception ->
        _dialog.value = NoNetworkDialog(posBtnClicked = { _navigation.value = ToSettings() })
        Timber.e(exception)
    }
}