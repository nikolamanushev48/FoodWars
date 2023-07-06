package com.mentormate.foodwars.domain.vo.registration

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import com.mentormate.foodwars.BR
import com.mentormate.foodwars.ui.constants.INVALID_RES

data class RegistrationUIModel(
    var rememberMe: Int = 0,
    var confirmPassword: String = "",
    var password: String = "",
    val subNomenclatures: List<String> = emptyList(),
    val interestPosition: ObservableField<Int> = ObservableField()
) : BaseObservable() {

    @Bindable
    var firstName = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.firstName)
        }

    @Bindable
    var lastName = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.lastName)
        }

    @Bindable
    var emailText = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.emailText)
        }

    @get:Bindable
    var firstNameError: Int = INVALID_RES
        set(value) {
            field = value
            notifyPropertyChanged(BR.firstNameError)
        }

    @get:Bindable
    var lastNameError: Int = INVALID_RES
        set(value) {
            field = value
            notifyPropertyChanged(BR.lastNameError)
        }

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

    @get:Bindable
    var confirmPasswordError: Int = INVALID_RES
        set(value) {
            field = value
            notifyPropertyChanged(BR.confirmPasswordError)
        }
}