package com.mentormate.foodwars.ui.motivation

import androidx.constraintlayout.motion.widget.MotionLayout

class MotivationListener(private val callBack: GoBack) : MotionLayout.TransitionListener {

    override fun onTransitionStarted(
        motionLayout: MotionLayout?,
        startId: Int,
        endId: Int
    ) {
        //do nothing
    }

    override fun onTransitionChange(
        motionLayout: MotionLayout?,
        startId: Int,
        endId: Int,
        progress: Float
    ) {
        //do nothing
    }

    override fun onTransitionCompleted(
        motionLayout: MotionLayout?,
        currentId: Int
    ) {
        if (currentId == motionLayout?.endState) {
            callBack.goBack()
        }
    }

    override fun onTransitionTrigger(
        motionLayout: MotionLayout?,
        triggerId: Int,
        positive: Boolean,
        progress: Float
    ) {
        //do nothing
    }
}