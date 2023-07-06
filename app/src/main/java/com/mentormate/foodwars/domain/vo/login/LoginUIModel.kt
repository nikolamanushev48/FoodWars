package com.mentormate.foodwars.domain.vo.login

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.mentormate.foodwars.BR
import com.mentormate.foodwars.ui.constants.INVALID_RES

data class LoginUIModel(
    var emailText: String = "",
    var password: String = ""
) : BaseObservable() {

    @get:Bindable
    var emailError: Int = INVALID_RES
        set(value) {
            field = value
            notifyPropertyChanged(BR.emailError)
        }

    @get:Bindable
    var passwordError: Int = INVALID_RES
        set(value) {
            field = value
            notifyPropertyChanged(BR.passwordError)
        }
}