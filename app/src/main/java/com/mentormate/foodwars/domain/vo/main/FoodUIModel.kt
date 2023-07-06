package com.mentormate.foodwars.domain.vo.main

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.mentormate.foodwars.BR
import com.mentormate.foodwars.R
import com.mentormate.foodwars.data.ScaleRating
import com.mentormate.foodwars.ui.constants.INVALID_ID
import com.mentormate.foodwars.ui.constants.INVALID_RES

data class FoodUIModel(
    val id: Long = INVALID_ID,
    val name: String = "",
    var imageUrl: String = "",
    val type: Int = INVALID_RES,
    val rating: ScaleRating = ScaleRating.NORMAL
) : BaseObservable(){
    @Bindable
    var background: Int = R.drawable.card_background
        set(value) {
            field = value
            notifyPropertyChanged(BR.background)
        }
}