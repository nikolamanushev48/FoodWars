package com.mentormate.foodwars

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

private const val TIMER_DELAY = 5000

class ClickerTimer(
    lifecycle: Lifecycle,
    val callback: (Boolean) -> Unit
) : DefaultLifecycleObserver {

    private var timeInMilli = System.currentTimeMillis()

    init {
        lifecycle.addObserver(this)
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)

        //if (System.currentTimeMillis() - timeInMilli >= TIMER_DELAY) {
            callback(true)
        //}
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)

        timeInMilli = System.currentTimeMillis()
    }
}