package com.mentormate.foodwars.domain.vo.profile

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.mentormate.foodwars.BR
import com.mentormate.foodwars.ui.constants.INVALID_ID

data class UserUIModel(
    val id: Long = INVALID_ID,
    val fullName: String = "",
    val email: String = "",
    val gender: String = ""
) : BaseObservable() {

    @Bindable
    var picture: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.picture)
        }
}