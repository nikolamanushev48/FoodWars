package com.mentormate.foodwars.ui.motivation

import androidx.lifecycle.LifecycleObserver
import com.mentormate.foodwars.data.BaseViewModel
import com.mentormate.foodwars.data.PopBack
import javax.inject.Inject

interface GoBack {
    fun goBack()
}

class MotivationViewModel @Inject constructor() : BaseViewModel(), LifecycleObserver, MotivationPresenter, GoBack {
    override fun goBack() {
        _navigation.value = PopBack
    }
}