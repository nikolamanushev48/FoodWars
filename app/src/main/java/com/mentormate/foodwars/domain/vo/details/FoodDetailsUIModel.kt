package com.mentormate.foodwars.domain.vo.details

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.mentormate.foodwars.BR
import com.mentormate.foodwars.data.ScaleRating
import com.mentormate.foodwars.data.room.Characteristic
import com.mentormate.foodwars.data.room.RelatedItemTable
import com.mentormate.foodwars.ui.constants.INVALID_RES

data class FoodDetailsUIModel(
    val foodName: String = "",
    val imageResource: String = "",
    val type: Int = INVALID_RES,
    val rating: ScaleRating = ScaleRating.NORMAL,
    val listRelatedItems: List<RelatedItemTable> = emptyList(),
    val characteristics: List<Characteristic> = emptyList()
) : BaseObservable() {
    @Bindable
    var buttonVisibility: Boolean = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.buttonVisibility)
        }

    @Bindable
    var ratingTextViewVisibility: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.ratingTextViewVisibility)
        }
}