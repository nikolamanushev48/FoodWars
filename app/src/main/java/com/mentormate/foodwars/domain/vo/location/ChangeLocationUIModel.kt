package com.mentormate.foodwars.domain.vo.location

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.mentormate.foodwars.BR
import com.mentormate.foodwars.R
import com.mentormate.foodwars.ui.constants.INVALID_RES

class ChangeLocationUIModel(
    val emotionFaceImageResource: Int = INVALID_RES,
    val emotionStateTextResource: Int = INVALID_RES,
    val emotionStateTextSizeResource: Int = R.dimen.sadStateTextSize,
    var settingsButtonTextResource: Int = INVALID_RES,
    val emotionStateTextVisibility: Boolean = false,
    val emotionFaceImageViewVisibility: Boolean = false,
    val settingsButtonVisibility: Boolean = false,
    val changeButtonVisibility: Boolean = true,
    val descriptionTextViewVisibility: Boolean = true,
    val motivationTextViewVisibility: Boolean = true,
    var resetData: Boolean = false,
    val numberOfFoods: Int = INVALID_RES
) : BaseObservable() {
    @Bindable
    var animationViewVisibility: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.animationViewVisibility)
        }

}